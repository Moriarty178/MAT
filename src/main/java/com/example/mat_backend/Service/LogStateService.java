package com.example.mat_backend.Service;

import com.example.mat_backend.Entity.LogState;
import com.example.mat_backend.Repository.LogStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LogStateService {
    @Autowired
    LogStateRepository logStateRepository;

    public List<LogState> getStates(int offset, int limit) {
        int pageNumber = offset / limit;
//        Pageable pageable = PageRequest.of(pageNumber, limit); //khởi tạo phân trang(pageNumber, limit)
//        Page<LogState> resulftPage = logStateRepository.getAllStates(pageable);//search theo query có phân trang
        //lấy phân trang với pagenumber và limit, sắp xếp theo updateAt giảm dần.
        Pageable pageable1 = PageRequest.of(pageNumber, limit, Sort.by(Sort.Direction.DESC, "updatedAt"));//khởi tạo phần trang(pageNumber, limit) kèm sắp xếp giảm dần theo ID
        return logStateRepository.findAll(pageable1).getContent();       //ok done
//        return resulftPage.getContent();
    }
}
