package com.example.mat_backend.Service;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Repository.FileStorageRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

    public void uploadFile(MultipartFile file) throws IOException {
        String fileType = getFileExtension(file.getOriginalFilename());
        String encodedContent;
        String encoding = "UTF-8";

        if("csv".equalsIgnoreCase(fileType)) {
            encodedContent = encodeCsvFile(file);
        } else if ("xlsx".equalsIgnoreCase(fileType)) {
            encodedContent = encodeXlsxFile(file);
        } else {
            throw new UnsupportedOperationException("Unsupported file type: " + fileType);
        }

        FileStorage fileStorage = new FileStorage();
        fileStorage.setUuid(UUID.randomUUID().toString());
        fileStorage.setName(file.getOriginalFilename());
        fileStorage.setType(fileType);
        fileStorage.setFileSize(file.getSize());
        fileStorage.setFi_buffer(encodedContent);
        fileStorage.setFi_encoding(encoding);
        fileStorage.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        fileStorage.setUpdatedAt(new Timestamp(new Date().getTime()));

        fileStorageRepository.save(fileStorage);
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

    private String encodeCsvFile(MultipartFile file) throws IOException {
        try(Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
            CSVReader csvReader = new CSVReader(reader);
            StringWriter writer = new StringWriter()) {

            List<String[]> allRows;
            try {
                allRows = csvReader.readAll();
            } catch (CsvException e) {
                throw new IOException("Error parsing CSV file", e);
            }
            for(String[] row : allRows) {
                String rowString = String.join(",", row);
                writer.write(rowString);
                writer.write("\n");
            }

            return writer.toString();
        }
    }

    private String encodeXlsxFile(MultipartFile file) throws  IOException {
        try(InputStream inputStream = file.getInputStream();
            Workbook workbook = new XSSFWorkbook(inputStream);
            StringWriter writer = new StringWriter()) {

            Sheet sheet = workbook.getSheetAt(0);
            for(Row row : sheet) {
                StringBuilder rowString = new StringBuilder();
                for(Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowString.append(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if(DateUtil.isCellDateFormatted(cell))
                                rowString.append(cell.getDateCellValue());
                            else
                                rowString.append(cell.getNumericCellValue());
                            break;
                        case BOOLEAN:
                            rowString.append(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            rowString.append(cell.getCellFormula());
                            break;
                        case BLANK:
                            rowString.append("");
                            break;
                        case ERROR:
                            rowString.append(cell.getErrorCellValue());
                            break;
                        default:
                            rowString.append(cell.toString());
                            break;
                    }

                    rowString.append(",");
                }
                rowString.deleteCharAt(rowString.length() - 1);
                writer.write(rowString.toString());
                writer.write("\n");
            }
            return writer.toString();
        }
    }


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
