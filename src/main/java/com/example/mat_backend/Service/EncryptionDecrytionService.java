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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionDecrytionService {
    @Autowired
    @Qualifier("redisTemplateRunFlow")
    private RedisTemplate<String, Object> redisRunFlowInstance;

    private static final String ALGORITHM = "AES";

    // Tạo SecretKey sử dụng MD5
    public SecretKey createSecretKey(String secret) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] key = md5.digest(secret.getBytes());
        return new SecretKeySpec(key, ALGORITHM);
    }

    // Mã hóa dữ liệu sử dụng AES
    public String encrypt(String data, SecretKey secretKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    // Giải mã dữ liệu sử dụng key tương ứng key mã hóa
    public String decrypt(String encryptedData, String secretKey) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
        return new String(decryptedBytes);
    }

    // Thêm dữ liệu được mã hóa vào Redis stream RunnerFlowStream
    public void addEncryptedDataToStream(String flowUuid, String eventName) throws Exception {
        String secret = "AFDSFH*134@473343499899";
        SecretKey secretKey = createSecretKey(secret);
        String encryptedData = encrypt(flowUuid, secretKey);
        String secretKeyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        String decryptedData = decrypt(encryptedData, secretKeyString);

        String timestamp = String.valueOf(Instant.now().toEpochMilli());
        Map<String, String> data = new HashMap<>();
        data.put("data_hashed", encryptedData);
        data.put("event_name", eventName);
        data.put("timestamp", timestamp);
        data.put("decrypData", decryptedData);

        MapRecord<String, String, String> record = MapRecord.create("RunnerFlowStream", data);
        RecordId recordId = redisRunFlowInstance.opsForStream().add(record);
        System.out.println("Entry added to stream with ID: " + recordId);
    }
}
