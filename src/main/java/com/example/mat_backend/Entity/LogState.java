package com.example.mat_backend.Entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Log_State")
public class LogState {
    @Id
    @Column(name = "lo_uuid", length = 255)
    private String uuid;

    @Column(name = "lo_st_uuid", nullable = false)
    private String toFnUuid;

    @Column(name = "lo_st_response", length = 255, nullable = false)
    private String stResponse;

    @Column(name = "lo_st_fl_uuid")
    private String flowUuid;

    @Column(name = "lo_created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "lo_updated_at", nullable = false)
    private Timestamp updatedAt;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getToFnUuid() {
        return toFnUuid;
    }

    public void setToFnUuid(String toFnUuid) {
        this.toFnUuid = toFnUuid;
    }

    public String getStResponse() {
        return stResponse;
    }

    public void setStResponse(String stResponse) {
        this.stResponse = stResponse;
    }

    public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

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
}