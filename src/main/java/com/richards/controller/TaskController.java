package com.richards.controller;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.TaskDto;
import com.richards.entity.Task;
import com.richards.enums.Status;
import com.richards.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/tasks")
    public ApiResponse<List<Task>> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/task/{taskId}/view")
    public ApiResponse<Task> viewTask(@PathVariable Long taskId) {
        return taskService.viewTask(taskId);
    }

    @GetMapping("/task/completed")
    public  ApiResponse<List<Task>> viewCompletedTasks() {
        return taskService.viewTasksByStatus(Status.COMPLETED);
    }

    @GetMapping("/task/in-progress")
    public ApiResponse<List<Task>> viewTasksInProgress() {
        return taskService.viewTasksByStatus(Status.IN_PROGRESS);
    }

    @GetMapping("/task/pending")
    public ApiResponse<List<Task>> viewPendingTasks() {
        return taskService.viewTasksByStatus(Status.PENDING);
    }


    @PostMapping("/task/create")
    public ApiResponse<Task> createNewTask(@RequestBody TaskDto taskDto) {
        return taskService.createNewTask(taskDto);
    }

    @PutMapping("/task/{taskId}/update")
    public ApiResponse<Task> updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskId, taskDto);
    }

    @DeleteMapping("/task/{taskId}/delete")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask( taskId);
    }

    @DeleteMapping("/task/deleteAll")
    public void deleteAllTasks() {
        taskService.deleteAllTasks();
    }
}
