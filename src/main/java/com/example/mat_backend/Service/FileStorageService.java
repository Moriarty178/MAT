package com.example.mat_backend.Service;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Repository.FileStorageRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FileStorageService {

    @Autowired
    FileStorageRepository fileStorageRepository;

    //FileStorage Management------------------------
    public void uploadFile(MultipartFile file) throws IOException {
        String fileType = getFileExtension(file.getOriginalFilename());
        String encodedContent;
        String encoding = "UTF-8";

        if("csv".equalsIgnoreCase(fileType)) {
            encodedContent = encodeCsvFile1(file);
        } else if ("xlsx".equalsIgnoreCase(fileType)) {
            encodedContent = encodeXlsxFile1(file);
        } else {
            throw new UnsupportedOperationException();
        }

        FileStorage fileStorage = new FileStorage();
        fileStorage.setUuid(UUID.randomUUID().toString());
        String fileNameFull = file.getOriginalFilename();
        fileStorage.setName(fileNameFull.substring(0, fileNameFull.lastIndexOf(".")));
//        fileStorage.setName(getFileName(file.getOriginalFilename()));
        fileStorage.setType(fileType);
//        fileStorage.setType(fileNameFull.substring(fileNameFull.lastIndexOf(".") + 1));
        fileStorage.setFileSize(file.getSize());
        fileStorage.setFi_content_json("{\"data\": \"content\"}");
        fileStorage.setFi_buffer(encodedContent);
        fileStorage.setFi_encoding(encoding);
        fileStorage.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        fileStorage.setUpdatedAt(new Timestamp(new Date().getTime()));

        fileStorageRepository.save(fileStorage);
    }


//    public String getFileName (String fileName) {
//        if (fileName == null || fileName.lastIndexOf(".") == -1) {
//            return "";
//        }
//        return fileName.substring(fileName.lastIndexOf(".") + 1);
//    }
    //get extension <=> "type"
    public String getFileExtension (String fileName) {
        if(fileName == null || fileName.lastIndexOf(".") == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    //search dựa theo id || name -> bản ghi có id || name chứa thông tin vừa gõ.
    public List<FileStorage> searchFile(String query) {
        if(query.startsWith("id:")) {
            String id = query.substring(3);
            return fileStorageRepository.findByIdContaining(id);
        } else if (query.startsWith("name:")) {
            String fiName = query.substring(5);
            return fileStorageRepository.findByNameContaining(fiName);
        } else {
            return List.of();
        }
    }

//    private String encodeCsvFile(MultipartFile file) throws IOException {
//        try(Reader reader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
//            CSVReader csvReader = new CSVReader(reader);
//            StringWriter writer = new StringWriter()) {
//
//            List<String[]> allRows;
//            try {
//                allRows = csvReader.readAll();
//            } catch (CsvException e) {
//                throw new IOException("Error parsing CSV file", e);
//            }
//            for(String[] row : allRows) {
//                String rowString = String.join("|", row);
//                writer.write(rowString);
//                writer.write("\n");
//            }
//
//            return writer.toString();
//        }
//    }
//
//    private String encodeXlsxFile(MultipartFile file) throws  IOException {
//        try(InputStream inputStream = file.getInputStream();
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            StringWriter writer = new StringWriter()) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            for(Row row : sheet) {
//                StringBuilder rowString = new StringBuilder();
//                for(Cell cell : row) {
//                    switch (cell.getCellType()) {
//                        case STRING:
//                            rowString.append(cell.getStringCellValue());
//                            break;
//                        case NUMERIC:
//                            if(DateUtil.isCellDateFormatted(cell))
//                                rowString.append(cell.getDateCellValue());
//                            else
//                                rowString.append(cell.getNumericCellValue());
//                            break;
//                        case BOOLEAN:
//                            rowString.append(cell.getBooleanCellValue());
//                            break;
//                        case FORMULA:
//                            rowString.append(cell.getCellFormula());
//                            break;
//                        case BLANK:
//                            rowString.append("");
//                            break;
//                        case ERROR:
//                            rowString.append(cell.getErrorCellValue());
//                            break;
//                        default:
//                            rowString.append(cell.toString());
//                            break;
//                    }
//
//                    rowString.append("|");
//                }
//                rowString.deleteCharAt(rowString.length() - 1);
//                writer.write(rowString.toString());
//                writer.write("\n");
//            }
//            return writer.toString();
//        }
//    }

    //Xử lý nội dung file CSV
    private String encodeCsvFile1(MultipartFile file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter bufferedWriter = new BufferedWriter((new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)))) {

            String line;
            while ((line = bufferedReader.readLine())!= null) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error processing CSV file", e);
        }

        //Chuyển đổi nội dung byte array thành chuỗi base64 để lưu vào "fi_buffer"
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    // Xử lý nội dung file Excel
    public String encodeXlsxFile1(MultipartFile file) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (InputStream inputStream = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
             BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8))) {

            // Lấy sheet đầu tiên                                  
            Sheet sheet = workbook.getSheetAt(0);                      //-> == !=

            // Đọc dữ liệu từ các hàng và cột
            for (Row row : sheet) {
                StringBuilder rowString = new StringBuilder();
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            rowString.append(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                rowString.append(cell.getDateCellValue());
                            } else {
                                rowString.append(cell.getNumericCellValue());
                            }
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
                    rowString.append(","); // Phân cách các cột
                }
                if (rowString.length() > 0) {
                    rowString.deleteCharAt(rowString.length() - 1); // Xóa dấu "," cuối cùng
                }
                bufferedWriter.write(rowString.toString());
                bufferedWriter.newLine(); // Dòng mới
            }
        } catch (IOException e) {
            throw new IOException("Error processing XLSX file", e);
        }

        // Chuyển đổi nội dung byte array thành chuỗi Base64
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    //Phục hồi, giải mã dữ liệu trong "fi_buffer" về nội dung ban đầu trong file CSV, XLSX    checked
    public String decodeAndProcessBase64Content(String base64Content) {
        //Giải mã chuỗi base64 -> byte array
        byte[] decodedBytes = Base64.getDecoder().decode(base64Content);
        String decodedString = new String(decodedBytes, StandardCharsets.UTF_8);//Chuyển byte array sang string sử dụng mã hóa UTF-8: MT convert byte-> ký tự (theo UTF-8)

        //Sử dụng biểu thức chính quy lại bỏ phần tập phần vd: 6379.0, 1.0, 2.0, 3.0
        Pattern decimanlPattern = Pattern.compile("(\\d+\\.)(0+)(,|\\s|$)");
        Matcher matcher = decimanlPattern.matcher(decodedString);

        //Thay thế các só thập phân thành số nguyên
        String processedString = matcher.replaceAll("$1$3");

        return processedString;
    }

    //Import fileStorage-----------------
    public List<FileStorage> getFileStorage(int offset, int limit) {
        int pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Direction.DESC, "uuid"));//khởi tạo phần trang(pageNumber, limit) kèm sắp xếp giảm dần theo ID
        return fileStorageRepository.findAll(pageable).getContent();       //ok done
    }

    public List<FileStorage> searchFileStorage(String query, int offset, int limit) {
        try {
            int pageNumber = offset / limit;
            Pageable pageable = PageRequest.of(pageNumber, limit); //khởi tạo phân trang(pageNumber, limit)
            Page<FileStorage> resulftPage = fileStorageRepository.searchFileStorage(query, pageable);//search theo query có phân trang
            return resulftPage.getContent();
        } catch (Exception e) {
           // System.err.println(e);
           // e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
         //   e.printStackTrace(); // Prints stack trace for detailed debugging
            return Collections.emptyList(); // Return an empty list in case of an error
        }
    }



}
