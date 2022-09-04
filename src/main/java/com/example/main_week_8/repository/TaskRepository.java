package com.example.main_week_8.repository;

import com.example.main_week_8.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query(value = "SELECT * FROM task WHERE status = ?1 AND user_id = ?2" , nativeQuery = true)
    List<Task> listOfTasksByStatus(String Status , int user_id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE task SET status = ?1 where  id = ?2" , nativeQuery = true)
    boolean updateTaskByIdAndStatus(String status , int id);

    @Query(value = "SELECT * FROM task WHERE user_id =?1", nativeQuery = true)
    List<Task> listOfTasksByUserId(int id);
}


