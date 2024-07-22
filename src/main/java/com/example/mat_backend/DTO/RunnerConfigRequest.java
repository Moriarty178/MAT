package com.example.mat_backend.DTO;

import com.example.mat_backend.Entity.Flow;

import java.sql.Timestamp;
public class RunnerConfigRequest {
    private String name;
    private String flow;
    private Integer timeout = 10;
    private Integer sleepBeforeNextRunTime;
    private Integer runTimes;
    private String type;
    private String cronjobFormat;

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
}

