package com.example.mat_backend.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

//@Entity
//@Table(name = "Runner_Configs")
//public class RunnerConfig {
//
//    //    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    @Column(name = "ru_co_id")
//    private Integer ruCoId;//uuid
//
//    //    @Column(name = "ru_co_name", nullable = false, length = 255)
//    private String ruCoName; //name
//
//    //    @Column(name = "ru_co_flow_id", nullable = true)
//    private Integer ruCoFlowId;//flow
//
//    //    @Column(name = "ru_co_state_id", nullable = true)
//    private Integer ruCoStateId;
//
//    //    @Column(name = "ru_co_timeout", nullable = false)
//    private Integer ruCoTimeout = 10;//time out
//
//    //    @Column(name = "ru_sleep_before_next_run_time", nullable = true)
//    private Integer ruSleepBeforeNextRunTime;//sleep
//
//    //    @Column(name = "ru_co_max_run", nullable = false)
//    private Integer ruCoMaxRun = 1;
//
//    //    @Column(name = "ru_co_type", nullable = false, length = 255)
//    private String ruCoType;//type
//
//    //    @Column(name = "ru_co_cronjob_format", nullable = true, length = 255)
//    private String ruCoCronjobFormat;//cronjobFormat
//
//    //    @Column(name = "ru_co_created_at", nullable = false)
//    private LocalDateTime ruCoCreatedAt = LocalDateTime.now();//create
//
//    //    @Column(name = "ru_co_updated_at", nullable = false)
//    private LocalDateTime ruCoUpdatedAt = LocalDateTime.now();//update
//
////    public Integer getRuCoId() {
////        return ruCoId;
////    }
////
////    public void setRuCoId(Integer ruCoId) {
////        this.ruCoId = ruCoId;
////    }
////
////    public String getRuCoName() {
////        return ruCoName;
////    }
////
////    public void setRuCoName(String ruCoName) {
////        this.ruCoName = ruCoName;
////    }
////
////    public Integer getRuCoFlowId() {
////        return ruCoFlowId;
////    }
////
////    public void setRuCoFlowId(Integer ruCoFlowId) {
////        this.ruCoFlowId = ruCoFlowId;
////    }
////
////    public Integer getRuCoStateId() {
////        return ruCoStateId;
////    }
////
////    public void setRuCoStateId(Integer ruCoStateId) {
////        this.ruCoStateId = ruCoStateId;
////    }
////
////    public Integer getRuCoTimeout() {
////        return ruCoTimeout;
////    }
////
////    public void setRuCoTimeout(Integer ruCoTimeout) {
////        this.ruCoTimeout = ruCoTimeout;
////    }
////
////    public Integer getRuSleepBeforeNextRunTime() {
////        return ruSleepBeforeNextRunTime;
////    }
////
////    public void setRuSleepBeforeNextRunTime(Integer ruSleepBeforeNextRunTime) {
////        this.ruSleepBeforeNextRunTime = ruSleepBeforeNextRunTime;
////    }
////
////    public Integer getRuCoMaxRun() {
////        return ruCoMaxRun;
////    }
////
////    public void setRuCoMaxRun(Integer ruCoMaxRun) {
////        this.ruCoMaxRun = ruCoMaxRun;
////    }
////
////    public String getRuCoType() {
////        return ruCoType;
////    }
////
////    public void setRuCoType(String ruCoType) {
////        this.ruCoType = ruCoType;
////    }
////
////    public String getRuCoCronjobFormat() {
////        return ruCoCronjobFormat;
////    }
////
////    public void setRuCoCronjobFormat(String ruCoCronjobFormat) {
////        this.ruCoCronjobFormat = ruCoCronjobFormat;
////    }
////
////    public LocalDateTime getRuCoCreatedAt() {
////        return ruCoCreatedAt;
////    }
////
////    public void setRuCoCreatedAt(LocalDateTime ruCoCreatedAt) {
////        this.ruCoCreatedAt = ruCoCreatedAt;
////    }
////
////    public LocalDateTime getRuCoUpdatedAt() {
////        return ruCoUpdatedAt;
////    }
////
////    public void setRuCoUpdatedAt(LocalDateTime ruCoUpdatedAt) {
////        this.ruCoUpdatedAt = ruCoUpdatedAt;
////    }
//// Getters and setters
//
//
//}

@Entity
@Table(name = "Runner_Configs")
public class RunnerConfig {

    @Id
    @Column(name = "ru_co_uuid", length = 255)
    private String uuid;

    @Column(name = "ru_co_name", length = 255, nullable = false)
    private String name;

    @JoinColumn(name = "ru_co_flow_uuid", referencedColumnName = "fl_uuid", unique = true)
    private String flow;

    @Column(name = "ru_co_timeout", nullable = false, columnDefinition = "INTEGER DEFAULT 10")
    private Integer timeout = 10;

    @Column(name = "ru_sleep_before_next_run_time")
    private Integer sleepBeforeNextRunTime;

    @Column(name = "ru_co_run_times")
    private Integer runTimes;

    @Column(name = "ru_co_type", length = 255, nullable = false)
    private String type;

    @Column(name = "ru_co_cronjob_format", length = 255)
    private String cronjobFormat;

    @Column(name = "ru_co_created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "ru_co_updated_at", nullable = false)
    private Timestamp updatedAt;

    // Getters and setters


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getSleepBeforeNextRunTime() {
        return sleepBeforeNextRunTime;
    }

    public void setSleepBeforeNextRunTime(Integer sleepBeforeNextRunTime) {
        this.sleepBeforeNextRunTime = sleepBeforeNextRunTime;
    }

    public Integer getRunTimes() {
        return runTimes;
    }

    public void setRunTimes(Integer runTimes) {
        this.runTimes = runTimes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCronjobFormat() {
        return cronjobFormat;
    }

    public void setCronjobFormat(String cronjobFormat) {
        this.cronjobFormat = cronjobFormat;
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