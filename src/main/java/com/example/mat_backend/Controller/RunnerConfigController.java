package com.example.mat_backend.Controller;

import com.example.mat_backend.DTO.RunnerConfigRequest;
import com.example.mat_backend.Service.EncryptionDecrytionService;
import com.example.mat_backend.Service.RunnerConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequestMapping("/api/runner-configs")
public class RunnerConfigController {

    @Autowired
    private RunnerConfigService runnerConfigService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private EncryptionDecrytionService encryptionDecrytionService;

    @PostMapping("/save/create")//Click button Save
    public ResponseEntity<String> createRunnerConfig(@RequestBody RunnerConfigRequest request, @RequestParam String flowUuid) {
        runnerConfigService.createRunnerConfig(request);
        //Kiểm tra trờng ru_co_cronjob_format trước khi đẩy lên stream "runnerflow"
        if(request.getCronjobFormat() != null) {
            String eventName = "save_runner_flow";
            String data = flowUuid;
            //Đẩy lên stream
            try {
                encryptionDecrytionService.addEncryptedDataToStream(data, eventName);
                return ResponseEntity.ok("Successfully uploaded to stream.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error pushing to stream: " + e.getMessage());
            }
        }
        return ResponseEntity.status(500).body("Cronjob format is null");
    }

    @PutMapping("/save/update/{Uuid}")//Click button Save
    public ResponseEntity<String> updateRunnerConfig(@RequestBody RunnerConfigRequest request, @PathVariable String Uuid, @RequestParam String flowUuid) {
        runnerConfigService.updateRunnerConfig(request, Uuid);
        //Kiểm tra trờng ru_co_cronjob_format trước khi đẩy lên stream "runnerflow"
        if(request.getCronjobFormat() != null) {
            String eventName = "save_runner_flow";
            String data = flowUuid;
            //Đẩy lên stream
            try {
                encryptionDecrytionService.addEncryptedDataToStream(data, eventName);
                return ResponseEntity.ok("Successfully uploaded to stream.");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(500).body("Error pushing to stream: " + e.getMessage());
            }
        }
        return ResponseEntity.status(500).body("Cronjob format is null");
    }
}

//public ResponseEntity<String> saveRunnerConfig(@RequestBody RunnerConfigRequest request, @RequestParam String flowUuid) {
//    //Create or update Runner Config
//    runnerConfigService.saveOrUpdateRunnerConfig(request, flowUuid);
//    //Kiểm tra trờng ru_co_cronjob_format trước khi đẩy lên stream "runnerflow"
//    if(request.getCronjobFormat() != null) {
//        String eventName = "save_runner_flow";
//        String data = flowUuid;
//        //Đẩy lên stream
//        try {
//            encryptionDecrytionService.addEncryptedDataToStream(data, eventName);
//            return ResponseEntity.ok("Successfully uploaded to stream.");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error pushing to stream: " + e.getMessage());
//        }
//    }
//    return ResponseEntity.status(500).body("Cronjob format is null");
//}