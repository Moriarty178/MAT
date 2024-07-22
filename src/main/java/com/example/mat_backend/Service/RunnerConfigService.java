package com.example.mat_backend.Service;

import com.example.mat_backend.DTO.RunnerConfigRequest;
import com.example.mat_backend.Entity.RunnerConfig;
import com.example.mat_backend.Repository.RunnerConfigRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RunnerConfigService {

    @Autowired
    RunnerConfigRepository runnerConfigRepository;

    @Transactional
    public RunnerConfig createRunnerConfig(RunnerConfigRequest request) {
        RunnerConfig runnerConfig = new RunnerConfig();
//        Optional<RunnerConfig> optionalRunnerConfig = runnerConfigRepository.findById(request.getUuid());
//        if (optionalRunnerConfig.isPresent()) {
//            // Tồn tại => update Runner Config
//            runnerConfig = optionalRunnerConfig.get();
//        } else {//Không tồn tại => tạo mới
//            // Nếu không tìm thấy, ném ngoại lệ hoặc xử lý theo cách khác tùy thuộc vào yêu cầu
//            runnerConfig = new RunnerConfig();
//            runnerConfig.setUuid(request.getUuid());
//            runnerConfig.setCreatedAt(new Timestamp(new Date().getTime()));
//        }
        runnerConfig.setUuid(UUID.randomUUID().toString());
        runnerConfig.setName(request.getName());
        runnerConfig.setFlow(request.getFlow());
        runnerConfig.setRunTimes(request.getRunTimes());
        runnerConfig.setSleepBeforeNextRunTime(request.getSleepBeforeNextRunTime());
        runnerConfig.setTimeout(request.getTimeout());
        runnerConfig.setType(request.getType());
        runnerConfig.setCronjobFormat(request.getCronjobFormat());
        runnerConfig.setCreatedAt(new Timestamp(new Date().getTime()));
        runnerConfig.setUpdatedAt(new Timestamp(new Date().getTime()));//time update

        return runnerConfigRepository.save(runnerConfig);
    }

    @Transactional
    public RunnerConfig updateRunnerConfig(RunnerConfigRequest request, String Uuid) {
        RunnerConfig runnerConfig = new RunnerConfig();
        Optional<RunnerConfig> opRuCo = runnerConfigRepository.findById(Uuid);
        if(opRuCo.isPresent()) {
            runnerConfig = opRuCo.get();

            runnerConfig.setName(request.getName());
            runnerConfig.setFlow(request.getFlow());
            runnerConfig.setRunTimes(request.getRunTimes());
            runnerConfig.setSleepBeforeNextRunTime(request.getSleepBeforeNextRunTime());
            runnerConfig.setTimeout(request.getTimeout());
            runnerConfig.setType(request.getType());
            runnerConfig.setCronjobFormat(request.getCronjobFormat());
            runnerConfig.setUpdatedAt(new Timestamp(new Date().getTime()));//time update
        }

        return runnerConfigRepository.save(runnerConfig);
    }
}
