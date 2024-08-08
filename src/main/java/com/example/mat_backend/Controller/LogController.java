package com.example.mat_backend.Controller;

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
    private LogFlowRepository logFlowRepository;

    @Autowired
    private LogStateRepository logStateRepository;



    @GetMapping("/flows")        //ckecked
    public ResponseEntity<List<LogFlow>> getLogFlows(
            @RequestParam(value = "offset",defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        List<LogFlow> listLogFlow = logFlowService.getRecentLogFlow(offset, limit);//lấy 10 bản ghi mới nhất (lo_updated_at) bảng log_flow
        return ResponseEntity.ok(listLogFlow);
    }

    @GetMapping("/flows/load-more") //tải thêm khi người dùng chưa cick vào 1 log_flow cụ thể   checked
    public ResponseEntity<List<LogFlow>> loadMoreLogFlows(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "5") int limit ) {

        List<LogFlow> listLogMore;
        listLogMore = logFlowService.getRecentLogFlow(offset, limit);  //tải thêm 7 bản ngay sau 10 bản trước
        return ResponseEntity.ok(listLogMore);
    }

    @GetMapping("/flows/{flowUuid}/states") //lấy 7 bản ghi mới nhất (lo_updated_at) từ bảng log_state checked
    public ResponseEntity<List<LogState>> getLogStates(
            @PathVariable String flowUuid,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "7") int limit) {

        List<LogState> listLogState = logFlowService.getStatesByLogFlow(flowUuid, offset, limit);//lấy 7 bản log_state mới nhât của loUuid
        return ResponseEntity.ok(listLogState);//truy vấn sql bên repository
    }

    @GetMapping("/flows/{flowUuid}/states/load-more")//tải thêm khi người dùng đã chọn 1 log_flow cụ thể trước đó    checked
    public ResponseEntity<List<LogState>> loadMoreLogStates(
            @PathVariable (value = "flowUuid") String flowUuid,
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "3") int limit) {

        List<LogState> listLogState = logFlowService.getStatesByLogFlow(flowUuid, offset, limit);// lấy thêm 3 bản ghi lob_state ngay sau 7 bản trước
        return ResponseEntity.ok(listLogState);
    }

    
   
}
