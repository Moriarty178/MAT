package com.example.mat_backend.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionDecrytionService {
    @Autowired
    @Qualifier("redisTemplateRunFlow")//chỉ định tiêm Bean nào trong trường hợp có nhiều Bean RedisTemplate
    private RedisTemplate<String, Object> redisRunFlowInstance;

    private static final String ALGORITHM = "AES";

    //Create Key using MD5
    public SecretKey createSecretKey(String secret) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] key = md5.digest(secret.getBytes());
        return new SecretKeySpec(key, ALGORITHM);
    }

    //Encrypt dữ liệu sử dụng AES
    public String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);//tạo đối tượng cipher để mã hóa
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);//khởi tạo với trạng thái Encrypt + key = secretKey
        byte[]  encryptedBytes = cipher.doFinal(data.getBytes());//mảng byte[] dùng để lưu kết quả sau mã hóa
        return Base64.getEncoder().encodeToString(encryptedBytes);//convert byte[]->chuỗi ký tự (định dạng base64)
    }

    //Decrypt dữ liệu sử dụng key tương ứng key mã hóa
    public String decrypt(String encryptedData, String secretKey) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);//base64->secretKey (byte[]), có mảng byte[] -> tạo đối tượng SecretKey-với giá byte[] vừa giải mã.
        Cipher cipher =Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);//khởi tạo chế độ giải  mã, kèm key
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));//thực hiện giải mã và (và chuyển base64->byte[])
        return new String((decryptedBytes));
    }

    //Add dữ liệu được encrypted vào Redis stream RunnerFlowStream
    public void addEncryptedDataToStream(String flowUuid, String eventName) throws Exception {
        String secret = "AFDSFH*134@473343499899";//hashed md5: c57fa30d5bdb7d3523562987461665dc
        SecretKey secretKey = createSecretKey(secret);
        //Encrypt dữ liệu
        String encryptedData = encrypt(flowUuid, secretKey);
        String secretKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());//convert byte[]->base64 để lưu trong stream : xX+jDVvbfTUjVimHRhZl3A==
        String decryptedData = decrypt(encryptedData, secretKeyString);
        //Chuẩn bị để xadd
        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        Map<String, String> data = new HashMap<>();

        data.put("data_hashed", encryptedData);
        data.put("event_name", eventName);
        data.put("timestamp", timestamp);
        data.put("decrypData", decryptedData);

//        data.put("key_private", secretKeyString);
//        Map<String, String> data = new HashMap<>();
//        data.put("data_hashed", new String("encryptedData".getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
//        data.put("event_name", new String(eventName.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
//        data.put("timestamp", new String(timestamp.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));

        //Add data vào stream
        MapRecord<String, String, String> record = MapRecord.create("RunnerFlowStream", data);//tạo một mục mới trong stream RunnerFlowStream, nếu stream này chưa có thì tạo, ngược lại thì mục mới được thêm vào stream hện có mà không tạo ra stream mới.
        RecordId recordId = redisRunFlowInstance.opsForStream().add(record);//add vào stream
        System.out.println("Entry added to stream with ID: " + recordId);
    }

    //




}
