package com.example.mat_backend.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Flows")
public class Flow {

    @Id
    @Column(name = "fl_uuid")
    private String flowUuid;

    @Column(name = "fl_name", nullable = false)
    private String name;

    @Column(name = "fl_user_id", nullable = false)
    private String userId;

    @Column(name = "fl_code", nullable = false)
    private String code;

    @Column(name = "fl_status", nullable = false)
    private String status;

    @Column(name = "fl_state_json", length = 12000, nullable = false)
    private String stateJson;

    @Column(name = "fl_created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "fl_updated_at", nullable = false)
    private LocalDateTime updatedAt;


}

