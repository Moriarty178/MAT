package com.example.mat_backend.Controller;

import com.example.mat_backend.Service.EncryptionDecrytionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/flows")
public class FlowController {

    @Autowired
    private EncryptionDecrytionService encryptionDecryp;

    //User click button Save(tại màn hình Runner Config) -> Create/Update runner config -> đẩy 1 event name save_runner_flow (chỉ đẩy lên RunnerFlowStream khi check flow này có trường ru_co_cronjob_format !== null)
//    @PostMapping("/create")
//    public ResponseEntity<String> createFlow(@RequestBody FlowRequest flowRequest, String secretKey) {
//        //Create/update runner config ***
//        String flowUuid = flowService.createFlow(flowRequest);
//        String eventName = "save_runner_flow";
//        String timestamp = String.valueOf(Instant.now().toEpochMilli());
//
//        // Tạo chuỗi JSON theo định dạng yêu cầu bằng cách nối chuỗi (lưu vào stream "run_flow")
//        String message = "["
//                + "[\\\"data_hashed\\\", \\\"" + flowUuid + "\\\"],"
//                + "[\\\"event_name\\\", \\\"" + eventName + "\\\"],"
//                + "[\\\"timestamp\\\", \\\"" + timestamp + "\\\"]"
//                + "]";
//        //Check ru_co_cronjob_format != null *** I
//
//        //Đẩy dữ liệu lên RunnerFlowStream II
//        try {
//            //Hàm đẩy event name "save flow" lên redis stream RunnerFlowStream
//            encryptionDecryp.addEncryptedDataToStream(message, eventName);
//            return ResponseEntity.ok(flowUuid);//gửi "flowUuid" -> Frontend.
//        } catch(Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Error saving flow: " + e.getMessage());
//        }
//    }
    //Khi user click button Run -> querry?flow_id=? call BE API -> đẩy 1 evnet name = run_flow
    @PostMapping("/run-flow")
    public ResponseEntity<String> runFlow(@RequestParam String flowUuid) {
        String data = flowUuid;
        //Đẩy sự kiện run flow vào RunnerFlowStream
        String eventName = "run_flow";
        try {
            encryptionDecryp.addEncryptedDataToStream(data, eventName);
            return ResponseEntity.ok("Successfully uploaded to stream.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error pushing to stream.");
        }
    }
}

