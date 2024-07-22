package com.example.mat_backend.Repository;

import com.example.mat_backend.Entity.RunnerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunnerConfigRepository extends JpaRepository<RunnerConfig, String> {
    Optional<RunnerConfig> findById(String flowUuid);
    RunnerConfig findByFlow(String flowUuid);
}
