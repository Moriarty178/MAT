package com.example.mat_backend.Repository;

import com.example.mat_backend.Entity.FileStorage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, String> {
    //Lầy những bản ghi có uuid || name chứa querry
    @Query("SELECT f from FileStorage f WHERE f.fi_uuid LIKE %:querry%")
    List<FileStorage> findByIdContaining(@Param("querry") String querry);

    @Query("SELECT f from FileStorage f WHERE f.fi_name LIKE %:querry")
    List<FileStorage> findByNameContaining(@Param("querry") String querry);
}
