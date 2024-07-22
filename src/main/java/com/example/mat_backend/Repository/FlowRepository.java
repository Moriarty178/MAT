package com.example.mat_backend.Repository;


import com.example.mat_backend.Entity.Flow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowRepository  extends JpaRepository <Flow, String> {
//    void deleteByFlowUuid(String flowUuid);
//
//    Flow findByFlowUuid(String flowUuid);
//
//    List<Flow> findByUserId(String userId);

}
