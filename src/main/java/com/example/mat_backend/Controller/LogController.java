package com.example.mat_backend.Controller;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Entity.LogFlow;
import com.example.mat_backend.Entity.LogState;
import com.example.mat_backend.Repository.LogFlowRepository;
import com.example.mat_backend.Repository.LogStateRepository;
import com.example.mat_backend.Service.LogFlowService;
import com.example.mat_backend.Service.LogStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

//
//import com.example.mat_backend.Service.LogService;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
@RestController
@RequestMapping("api/logs")
public class LogController {
    @Autowired
    private LogFlowService logFlowService;

    @Autowired
    private LogStateService logStateService;

    @Autowired
    private LogFlow logFlow;

    @Autowired
    private LogState logState;

    @Autowired
    private LogFlowRepository logFlowRepository;

    @Autowired
    private LogStateRepository logStateRepository;



    @GetMapping("/flows")
    public ResponseEntity<List<LogFlow>> getLogFlows(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<LogFlow> listLogFlow = logFlowService.getRecentLogFlow(offset, limit);//lấy 10 bản ghi mới nhất (lo_updated_at) bảng log_flow
        return ResponseEntity.ok(listLogFlow);
    }

    @GetMapping("/flows/load-more") //tải thêm khi người dùng chưa cick vào 1 log_flow cụ thể
    public ResponseEntity<List<LogFlow>> loadMoreLogFlows(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "5") int limit ) {

        List<LogFlow> listLogMore;
        listLogMore = logFlowService.getRecentLogFlow(offset, limit);  //tải thêm 7 bản ngay sau 10 bản trước
        return ResponseEntity.ok(listLogMore);
    }

    @GetMapping("/flows/{loUuid}/states") //lấy 7 bản ghi mới nhất (lo_updated_at) từ bảng log_state
    public ResponseEntity<List<LogState>> getLogStates(
            @PathVariable String loUuid,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "7") int limit) {

        List<LogState> listLogState = logFlowService.getStatesByLogFlow(loUuid, offset, limit);//lấy 7 bản log_state mới nhât của loUuid
        return ResponseEntity.ok(listLogState);//truy vấn sql bên repository
    }

    @GetMapping("/flows/{loUuid}/states/load-more")//tải thêm khi người dùng đã chọn 1 log_flow cụ thể trước đó
    public ResponseEntity<List<LogState>> loadMoreLogStates(
            @PathVariable String loUuid,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "3") int limit) {

        List<LogState> listLogState = logFlowService.getStatesByLogFlow(loUuid, offset, limit);// lấy thêm 3 bản ghi lob_state ngay sau 7 bản trước
        return ResponseEntity.ok(listLogState);
    }

    
    //view all log flow
//    @GetMapping("/all_log_flow")
//    public ResponseEntity<List<LogFlow>> getAllLogFlows() {
//        //sắp xếp theo lo_updated_at
//        //findByLoUpdatedAt()
//        return ResponseEntity.ok(logFlowRepository.findAll());
//    }
//
//    //view all state của flowUuid cụ thể
//    @GetMapping("/all_log_state")  //click vào 1 flowUuid cụ thể
//    public ResponseEntity<List<LogState>> getAllLogStates(@RequestParam String flowUuid) {
//        //sắp xếp theo lo_updated_at
//        //getAllStateOfFlowUuid()
//        return ResponseEntity.ok(logStateRepository.findAll());
//    }
//
//    @GetMapping("/all_log_flow/loadmore")
//    public ResponseEntity<List<LogState>> loardMoreLogFlow(
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "7") int limit) {
//        List<LogState> listStates = logStateService.getStates(offset, limit);
//        return ResponseEntity.ok(listStates);
//    }
//    public ResponseEntity<List<FileStorage>> searchFileStorage(
//            @RequestParam(value = "query") String query,
//            @RequestParam(value = "offset", defaultValue = "0") int offset,
//            @RequestParam(value = "limit", defaultValue = "7") int limit) {
//        List<FileStorage> listFile = fileStorageService.searchFileStorage(query, offset, limit);
//        return ResponseEntity.ok(listFile);
//
//    @GetMapping("/all_log_state/loadmore")
//    public ResponseEntity<List<LogState>> loardMoreLogState() {
//
//    }

//
//    @Autowired
//    private LogService logService;
//
//    @GetMapping("/flowIds")
//    public List<String> getFlowIds() {
//        return logService.getAllFlowIds();
//    }
//
//    @GetMapping("/logslist")
//    public List<Map<Object, Object>> getLogs(@RequestParam String flowId) {
//        String streamName = "response_data_stream";
//        List<Map<String, Object>> instances = Arrays.asList(
//                createInstance("runFlow"),
//                createInstance("restfulApi"),
//                createInstance("redisTestTool"),
//                createInstance("tenserFlow")
//        );
//        return logService.getAllFilteredRecords(flowId, instances, streamName, 10, 10);
//    }
//
//    private Map<String, Object> createInstance(String instanceName) {
//        Map<String, Object> instance = new HashMap<>();
//        instance.put("instance", instanceName);
//        return instance;
//    }
}
