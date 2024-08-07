package com.example.mat_backend.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "Log_Flow")
public class LogFlow {
    @Id
    @Column(name = "lo_uuid", length = 255)
    private String uuid;

    @Column(name = "lo_fl_uuid", nullable = false)
    private String flowUuid;

    @Column(name = "lo_st_uuids", length = 255, nullable = false)
    private String stUuids;

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

    public String getFlowUuid() {
        return flowUuid;
    }

    public void setFlowUuid(String flowUuid) {
        this.flowUuid = flowUuid;
    }

    public String getStUuids() {
        return stUuids;
    }

    public void setStUuids(String stUuids) {
        this.stUuids = stUuids;
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