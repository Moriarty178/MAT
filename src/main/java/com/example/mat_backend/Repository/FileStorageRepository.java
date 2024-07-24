package com.example.mat_backend.Repository;

import com.example.mat_backend.Entity.FileStorage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage, String> {
    //Lầy những bản ghi có uuid || name chứa query
    @Query("SELECT f from FileStorage f WHERE f.fi_uuid LIKE %:query% ORDER BY f.fi_name")//sắp xếp theo Alphabet
    List<FileStorage> findByIdContaining(@Param("query") String query);

    @Query("SELECT f from FileStorage f WHERE f.fi_name LIKE %:query%")
    List<FileStorage> findByNameContaining(@Param("query") String query);

    //Tim kiếm theo "query" -> kết quả trả về được sắp xếp theo mức độ tương đồng giảm.
    @Query("SELECT f FROM FileStorage f WHERE f.fi_name LIKE %:query% ORDER BY " +
            "CASE " +
            "WHEN f.fi_name = :query THEN 0 " +
            "WHEN f.fi_name LIKE :query% THEN 1 " +
            "WHEN f.fi_name LIKE %:query THEN 2 " +
            "ELSE 3 END, " +
            "f.fi_name")     //cùng thứ hạng thì sắp xếp theo Alphabet
    Page<FileStorage> searchFileStorage(@Param("query") String query, Pageable pageable);//Truy vấn SQL theo "query" -> Sử dụng pageale để lấy những bản ghi với điều kiện offset, limit
}

//@Query("SELECT f FROM FileStorage f WHERE f.fiName LIKE CONCAT('%', :query, '%') " +
//        "ORDER BY " +
//        "CASE " +
//        "WHEN f.fiName = :query THEN 0 " +              // Hoàn toàn khớp với query
//        "WHEN f.fiName LIKE CONCAT(:query, '%') THEN 1 " + // Bắt đầu bằng query
//        "WHEN f.fiName LIKE CONCAT('%', :query) THEN 2 " + // Chứa query
//        "ELSE 3 END")//LIKE CONCAT('%', :query, '%')
