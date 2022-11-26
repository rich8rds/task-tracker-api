package com.richards.service;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.TaskDto;
import com.richards.entity.Task;
import com.richards.enums.Status;

import java.util.List;

public interface TaskService {
    ApiResponse<List<Task>> getAllTasks();

    ApiResponse<Task> createNewTask(TaskDto taskDto);

    ApiResponse<Task> updateTask(Long taskId, TaskDto taskDto);

    ApiResponse<Task> viewTask(Long taskId);

    ApiResponse<List<Task>> viewTasksByStatus(Status status);

    ApiResponse<String> deleteTask(Long taskId);

    ApiResponse<String> deleteAllTasks();

}
