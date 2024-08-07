package com.example.mat_backend.Repository;

import com.example.mat_backend.Entity.FileStorage;
import com.example.mat_backend.Entity.LogState;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogStateRepository extends JpaRepository<LogState, String> {
    //Tim kiếm theo "query" -> kết quả trả về được sắp xếp theo mức độ tương đồng giảm.
    @Query("SELECT f FROM LogState f WHERE f.uuid = query ORDER BY f.updatedAt")  //cùng thứ hạng thì sắp xếp theo Alphabet
    Page<LogState> searchLogState(@Param("query") String loUuid, Pageable pageable);//Truy vấn SQL theo "query" -> Sử dụng pageale để lấy những bản ghi với điều kiện offset, limit
}