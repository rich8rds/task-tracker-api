package com.richards.repository;

import com.richards.entity.Task;
import com.richards.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {
    @Query(value = "SELECT * FROM tasks WHERE user_id=?", nativeQuery = true)
    List<Task> findAllTasksById(Long userId);

//    @Query(value = "SELECT * FROM tasks WHERE user_id=? AND status=?", nativeQuery = true)
    List<Task> findAllByStatus(Status status);

    @Modifying
    @Query(value = "DELETE FROM tasks WHERE user_id=?", nativeQuery = true)
    void deleteAllByUserId(Long userId);
}
