package com.example.mat_backend.Service;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Entity.LogFlow;
import com.example.mat_backend.Entity.LogState;
import com.example.mat_backend.Repository.LogFlowRepository;
import com.example.mat_backend.Repository.LogStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LogFlowService {
    @Autowired
    LogFlowRepository logFlowRepository;

    @Autowired
    LogStateRepository logStateRepository;
    

    //Import fileStorage-----------------
    public List<LogFlow> getRecentLogFlow(int offset, int limit) {
        int pageNumber = offset / limit;
        Pageable pageable = PageRequest.of(pageNumber, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));//khởi tạo phần trang(pageNumber, limit) kèm sắp xếp giảm dần theo ID
        return logFlowRepository.findAll(pageable).getContent();       //ok done
    }

    public List<LogState> getStatesByLogFlow(String flowUuid, int offset, int limit) {
        try {
            int pageNumber = offset / limit;
            Pageable pageable = PageRequest.of(pageNumber, limit); //khởi tạo phân trang(pageNumber, limit)
            Page<LogState> resulftPage = logStateRepository.searchLogState(flowUuid, pageable);//search theo query có phân trang
            return resulftPage.getContent();
        } catch (Exception e) {
            // System.err.println(e);
            // e.printStackTrace();
            System.err.println("An error occurred: " + e.getMessage());
            //   e.printStackTrace(); // Prints stack trace for detailed debugging
            return Collections.emptyList(); // Return an empty list in case of an error
        }
    }
}
