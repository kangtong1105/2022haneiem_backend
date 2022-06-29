package com.example.demo.persistence;

import com.example.demo.medel.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    @Query(value = "select * from Todo t where t.USERID = ?1", nativeQuery = true)
    List<TodoEntity> findByUserId(String userId);
}
