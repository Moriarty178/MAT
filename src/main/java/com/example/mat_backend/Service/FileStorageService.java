package com.example.mat_backend.Service;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

    public FileStorage saveFile(MultipartFile file) throws IOException {
        FileStorage fileStorage = new FileStorage();
        //lưu meta data của file xlsx, csv vào PostgreSQL, phần nội dung (nặng tải) -> Sử dụng buffer
        fileStorage.setUuid(UUID.randomUUID().toString());
        fileStorage.setFileSize((int) file.getSize());
        fileStorage.setName(file.getOriginalFilename());
        fileStorage.setType(getFileExtension(file.getOriginalFilename()));
//        fileStorage.setContentJson(convertFileToJson(file));
        fileStorage.setCreatedAt(new Timestamp (new Date().getTime()));
        fileStorage.setUpdatedAt(new Timestamp (new Date().getTime()));

        return fileStorageRepository.save(fileStorage);
    }

    //get extension <=> "type"
    public String getFileExtension (String fileName) {
        if(fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    //search dựa theo id || name -> bản ghi có id || name chứa thông tin vừa gõ.
    public List<FileStorage> searchFile(String querry) {
        if(querry.startsWith("id:")) {
            String id = querry.substring(3);
            return fileStorageRepository.findByIdContaining(id);
        } else if (querry.startsWith("name:")) {
            String fiName = querry.substring(5);
            return fileStorageRepository.findByNameContaining(fiName);
        } else {
            return List.of();
        }
    }

    public
//    public List<FileStorage> getAllFiles() {
//        return fileStorageRepository.findAll();
//    }
//
//    public Optional<FileStorage> getFileById(String uuid) {
//        return fileStorageRepository.findById(uuid);
//    }
//
//    public void deleteFileById(String uuid) {
//        fileStorageRepository.deleteById(uuid);
//    }

    


}
