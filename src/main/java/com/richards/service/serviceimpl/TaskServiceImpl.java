package com.richards.service.serviceimpl;

import com.richards.apiresponse.ApiResponse;
import com.richards.dto.TaskDto;
import com.richards.entity.Task;
import com.richards.entity.User;
import com.richards.enums.Status;
import com.richards.exceptions.TaskNotFoundException;
import com.richards.exceptions.UserNotFoundException;
import com.richards.repository.TaskRepository;
import com.richards.repository.UserRepository;
import com.richards.service.TaskService;
import com.richards.session.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    private final HttpSession session;

    @Override
    public ApiResponse<List<Task>> getAllTasks() {
        Long userId = UserInfo.getUserSessionId(session);
        if(!userRepository.existsById(userId))
            throw new UserNotFoundException("User with id: " + userId + " does not exist.");
        return new ApiResponse<>("Tasks Fetched Successfully", true, taskRepository.findAllTasksById(userId));
    }

    @Override
    public ApiResponse<Task> createNewTask(TaskDto taskDto) {
        Long userId = UserInfo.getUserSessionId(session);
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User with id: " + userId + " does not exist."));
        Task task = Task.builder()
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .status(taskDto.getStatus())
                .user(user)
                .build();

        Task newTask = taskRepository.save(task);
        return new ApiResponse<>("Task created successfully", true, newTask);
    }

    @Override
    public ApiResponse<Task> updateTask(Long taskId, TaskDto taskDto) {
        Long userId = UserInfo.getUserSessionId(session);
        Optional<Task> oldOptionalTask = taskRepository.findById(taskId);
        Task oldTask = oldOptionalTask.orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " does not exist"));

        if(!oldTask.getUser().getId().equals(userId)) throw new UserNotFoundException("User with id " + userId + " not found.");

        String updatedTitle = taskDto.getTitle();
        String updatedDescription = taskDto.getDescription();

        if(updatedTitle != null && !updatedTitle.isBlank()) {
            oldTask.setTitle(updatedTitle);
        }

        if(updatedDescription != null && !updatedDescription.isBlank()) {
            oldTask.setDescription(updatedDescription);
        }

        oldTask.setStatus(taskDto.getStatus());

        taskRepository.save(oldTask);
        return new ApiResponse<>("Task updated successfully", true, oldTask);
    }

    @Override
    public ApiResponse<Task> viewTask(Long taskId) {
        Long userId = UserInfo.getUserSessionId(session);
        Optional<Task> oldOptionalTask = taskRepository.findById(taskId);
        Task task = oldOptionalTask.orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " does not exist"));

        if(!task.getUser().getId().equals(userId)) throw new UserNotFoundException("User with id " + userId + " not found.");
        return new ApiResponse<>("Task request successful", true, task);
    }

    @Override
    public ApiResponse<List<Task>> viewTasksByStatus(Status status) {
        Long userId = UserInfo.getUserSessionId(session);
        if(!userRepository.existsById(userId))
                throw new UserNotFoundException("User with id: " + userId + " does not exist.");

        List<Task> completedTasks = taskRepository.findAllByStatus(status);
        return new ApiResponse<>("Task request successful", true, completedTasks);
    }

    @Override
    public ApiResponse<String> deleteTask(Long taskId) {
        Long userId = UserInfo.getUserSessionId(session);
        Optional<Task> oldOptionalTask = taskRepository.findById(taskId);
        Task task = oldOptionalTask.orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " does not exist"));

        if(!task.getUser().getId().equals(userId)) throw new UserNotFoundException("User with id " + userId + " not found.");

        taskRepository.deleteById(taskId);
        return new ApiResponse<>("Task deleted successfully.", true, "TASK DELETED");
    }

    @Override
    public ApiResponse<String> deleteAllTasks() {
        Long userId = UserInfo.getUserSessionId(session);
        if(!userRepository.existsById(userId)) throw new UserNotFoundException("User with id + " + userId + "does not exist.");
        taskRepository.deleteAllByUserId(userId);
        return new ApiResponse<>("All Tasks deleted successfully.", true, "TASKS DELETED");
    }

}
