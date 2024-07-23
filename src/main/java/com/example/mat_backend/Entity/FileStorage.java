package com.example.mat_backend.Entity;

import jakarta.persistence.*;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "File_Storages")
public class FileStorage {

    @Id
    @Column(name = "fi_uuid", length = 255)
    private String uuid;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "fi_name", length = 255, nullable = false)
    private String name;

    @Column(name = "fi_type", length = 255, nullable = false)
    private String type;

//    @Column(name = "fi_content_json", length = 255, nullable = false)
//    private String contentJson;//Bỏ lưu trữ nội dung dạng JSON (nặng tải database)
    @Column(name = "fi_buffer", nullable = false)
    private String fi_buffer;

    @Column(name = "fi_encoding", nullable = false)
    private String fi_encoding;

    @Column(name = "fi_created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "fi_updated_at", nullable = false)
    private Timestamp updatedAt;

    // Getters and setters

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    public String getContentJson() {
//        return contentJson;
//    }
//
//    public void setContentJson(String contentJson) {
//        this.contentJson = contentJson;
//    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getFi_encoding() {
        return fi_encoding;
    }

    public void setFi_encoding(String fi_encoding) {
        this.fi_encoding = fi_encoding;
    }

    public String getFi_buffer() {
        return fi_buffer;
    }

    public void setFi_buffer(String fi_buffer) {
        this.fi_buffer = fi_buffer;
    }
}
