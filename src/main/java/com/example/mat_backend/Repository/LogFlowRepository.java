package com.example.mat_backend.Repository;

import com.example.mat_backend.Entity.LogFlow;
import com.example.mat_backend.Entity.LogState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogFlowRepository extends JpaRepository<LogFlow, String> {

}
