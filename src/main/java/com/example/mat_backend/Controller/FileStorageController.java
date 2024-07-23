package com.example.mat_backend.Controller;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Repository.FileStorageRepository;
import com.example.mat_backend.Service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/files")
public class FileStorageController {

    @Autowired
    FileStorageRepository fileStorageRepository;

    @Autowired
    FileStorageService fileStorageService;

    //upload
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            fileStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully with ID: " + file.getOriginalFilename());
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("Unsupported file type: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file: " + e.getMessage());
        }
    }

    //view all file storage
    @GetMapping
    public ResponseEntity<List<FileStorage>> getAllFiles() {
        return ResponseEntity.ok(fileStorageRepository.findAll()); //fileStorageService.getAllFiles()
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<FileStorage> getFileById(@PathVariable String uuid) {
        Optional<FileStorage> fileStorage = fileStorageRepository.findById(uuid);
//        FileStorage file = fileStorageRepository.findById(uuid).orElseThrow(() -> new RuntimeException("File not found"));
        return fileStorage.map((ResponseEntity::ok)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //delete with uuid
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> deleteFileById(@PathVariable String uuid) {
        fileStorageRepository.deleteById(uuid);//fileStorageService.deleteFileById(uuid);
        return ResponseEntity.ok("File deleted successfully");
    }

    //clone with uuid
    @PostMapping("/clone/{uuid}")
    public ResponseEntity<String> cloneFile(@PathVariable String uuid) {
        Optional<FileStorage> opFileStorage = fileStorageRepository.findById(uuid);
        if(opFileStorage.isPresent()){
            FileStorage file = opFileStorage.get();
            FileStorage fileStorage = new FileStorage();
            fileStorage.setUuid(UUID.randomUUID().toString());
            fileStorage.setFileSize(file.getFileSize());
            fileStorage.setName(file.getName());
            fileStorage.setType(file.getType());
            fileStorage.setFi_buffer(file.getFi_buffer());
            fileStorage.setFi_encoding(file.getFi_encoding());
            fileStorage.setCreatedAt(new Timestamp(new Date().getTime()));
            fileStorage.setUpdatedAt(new Timestamp (new Date().getTime()));
            fileStorageRepository.save(fileStorage);
            return ResponseEntity.ok("File cloned successfully with ID: " + fileStorage.getUuid());
        }
        return ResponseEntity.status(500).body("Error cloning file.");
    }

    //search with id or name
    @GetMapping("/search/{uuid}")
    public ResponseEntity<List<FileStorage>> searchFiles(@RequestParam String querry) {
        //Querry SQL với nameFile || fileUuid -> return records có name, uuid chứa querry.
        List<FileStorage> results = fileStorageService.searchFile(querry);
        return ResponseEntity.ok(results);
    }

    //Lọc --------------
    @GetMapping("/filter")
    public ResponseEntity<List<FileStorage>> filterFiles(@RequestParam String timeStart, @RequestParam String timeEnd) {
        //
        return ResponseEntity.ok(fileStorageRepository.findAll());//
    }
}
