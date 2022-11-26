package com.richards.controller;

import com.richards.dto.TaskDto;
import com.richards.entity.Task;
import com.richards.entity.User;
import com.richards.enums.Status;
import com.richards.exceptions.UserNotFoundException;
import com.richards.repository.TaskRepository;
import com.richards.repository.UserRepository;
import com.richards.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class TaskControllerTest {
    @Autowired
    private TaskService taskService;
    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    private List<Task> tasks;

    @Autowired
    private HttpSession session;

    @BeforeEach
    public void setUp() throws Exception {
        tasks = new ArrayList<>(List.of(
                Task.builder()
                        .taskId(1L)
                        .title("GROCERIES")
                        .description("Buy some groceries at the supermarket")
                        .user(User.builder()
                                .id(3L)
                                .firstName("Richards")
                                .lastName("Benson")
                                .email("richardsBenson@gmail.com")
                                .password("bensonMan")
                                .build())
                        .status(Status.PENDING).build(),
                Task.builder()
                        .taskId(2L)
                        .title("PAINTING")
                        .description("Paint the bedroom walls.")
                        .user(User.builder()
                                .id(3L)
                                .firstName("Richards")
                                .lastName("Benson")
                                .email("richardsBenson@gmail.com")
                                .password("bensonMan")
                                .build())

                        .status(Status.PENDING).build(),
                Task.builder()
                        .taskId(3L)
                        .title("DENTIST")
                        .description("Visit the dentist's for my regular check up.")
                        .user(User.builder()
                                .id(3L)
                                .firstName("Richards")
                                .lastName("Benson")
                                .email("richardsBenson@gmail.com")
                                .password("bensonMan")
                                .build())
                        .status(Status.IN_PROGRESS).build(),
                Task.builder()
                        .taskId(5L)
                        .title("SWIMMING")
                        .description("Going for a swim at Constantial hotel at 4:15 PM.")
                        .user(User.builder()
                                .id(3L)
                                .firstName("Richards")
                                .lastName("Benson")
                                .email("richardsBenson@gmail.com")
                                .password("bensonMan")
                                .build())
                        .status(Status.IN_PROGRESS).build(),
                Task.builder()
                        .taskId(4L)
                        .title("PIANO LESSONS")
                        .description("Piano lessons at 6:45 PM")
                        .user(User.builder()
                                .id(4L)
                                .firstName("Nikolai")
                                .lastName("Kohl")
                                .email("nikolaiKohl@gmail.com")
                                .password("nikolai")
                                .build())
                        .status(Status.COMPLETED).build()

        ));

//        session.setAttribute("userDetails", tasks.get(3).getUser());
    }

    @Test
    @DisplayName("Should retrieve all user's tasks from the mock database SUCCESSFULLY.")
    public void getAllTasksByUserId() {
        User user = tasks.get(3).getUser();
        session.setAttribute("userDetails", user);
        Long userId = user.getId();

        when(userRepository.existsById(userId)).thenReturn(true);
        assertTrue(userRepository.existsById(userId));

        when(taskRepository.findAllTasksById(userId))
                .thenReturn(tasks.stream()
                        .filter(x -> x.getUser().getId().equals(userId)).toList());

        assertEquals(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).count(),
                taskService.getAllTasks().getData().size());
    }


    @Test
    @DisplayName("Should throw UserNotFoundException when Id of user is not found!")
    public void getAllTasksByUserIdButEmpty() {
        User user = tasks.get(3).getUser();
        session.setAttribute("userDetails", user);
        assertThrows(UserNotFoundException.class, () -> taskService.getAllTasks());
        assertThrows(UserNotFoundException.class, () -> taskService.viewTasksByStatus(Status.IN_PROGRESS));
        assertThrows(UserNotFoundException.class, () -> taskService.viewTasksByStatus(Status.PENDING));
        assertThrows(UserNotFoundException.class, () -> taskService.viewTasksByStatus(Status.COMPLETED));
    }


    @Test
    @DisplayName("Creating a new task should be successful")
    public void createANewTask() {
        User user = User.builder()
                .id(2L)
                .firstName("Richards")
                .lastName("Benson")
                .email("richardsBenson@gmail.com")
                .password("bensonMan")
                .build();

        session.setAttribute("userDetails", user);

        Task task = Task.builder()
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .user(user)
                .build();

        when(taskRepository.save(task)).thenReturn(task);

        TaskDto taskDto = TaskDto.builder()
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .build();

        Long userId = user.getId();
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        assertEquals(2L, userRepository.findById(userId).orElse(User.builder().id(100L).build()).getId());

        when(taskRepository.save(task)).thenReturn(task);
        assertEquals(task, taskService.createNewTask(taskDto).getData());
    }

    @Test
    @DisplayName("Should throw UserNotFoundException")
    public void createANewTaskShouldThrowAnException() {
        User user = User.builder()
                .id(2L)
                .firstName("Richards")
                .lastName("Benson")
                .email("richardsBenson@gmail.com")
                .password("bensonMan")
                .build();

        session.setAttribute("userDetails", user);

        Task task = Task.builder()
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .user(user)
                .build();

        when(taskRepository.save(task)).thenReturn(task);

        TaskDto taskDto = TaskDto.builder()
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .build();

        when(userRepository.save(user)).thenReturn(user);
        assertThrows(UserNotFoundException.class, () -> taskService.createNewTask(taskDto));
    }

    @Test
    void updateUserDetails() {
        TaskDto taskDto = TaskDto.builder()
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .build();

        User user = User.builder()
                .id(3L)
                .firstName("Richards")
                .lastName("Benson")
                .email("richardsBenson@gmail.com")
                .password("bensonMan787")
                .build();

        session.setAttribute("userDetails", user);

        Task checkTask = tasks.get(2);

        when(taskRepository.save(checkTask)).thenReturn(checkTask);
        when(taskRepository.findById(checkTask.getTaskId())).thenReturn(Optional.ofNullable(checkTask));
        when(userRepository.save(user)).thenReturn(user);

        Task updatedTask = Task.builder()
                .taskId(3L)
                .title("DENTIST")
                .description("Visit the dentist's for my regular check up.")
                .user(user)
                .build();

        when(taskRepository.save(tasks.get(1))).thenReturn(updatedTask);
        assertEquals(updatedTask, taskService.updateTask(3L, taskDto).getData());
    }


    @Test
    @DisplayName("Should return the list of tasks with Status.COMPLETED")
    public void getCompletedTasks() {
        User user = tasks.get(4).getUser();
        Long userId = user.getId();
        session.setAttribute("userDetails", user);

        when(userRepository.existsById(userId)).thenReturn(true);
        assertTrue(userRepository.existsById(userId));

        when(taskRepository.findAllByStatus(Status.COMPLETED))
                .thenReturn(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).toList());

        assertEquals(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).count(),
                taskService.viewTasksByStatus(Status.COMPLETED).getData().size());
    }


    @Test
    @DisplayName("Should return the list of tasks with Status.IN_PROGRESS")
    public void getTasksInProgress() {
        User user = tasks.get(3).getUser();
        Long userId = user.getId();
        session.setAttribute("userDetails", user);


        when(userRepository.existsById(userId)).thenReturn(true);
        assertTrue(userRepository.existsById(userId));

        when(taskRepository.findAllByStatus(Status.IN_PROGRESS))
                .thenReturn(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).toList());

        assertEquals(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).count(),
                taskService.viewTasksByStatus(Status.IN_PROGRESS).getData().size());
    }

    @Test
    @DisplayName("Should return the list of tasks with Status.PENDING")
    public void getPendingTasks() {
        User user = tasks.get(3).getUser();
        Long userId = user.getId();
        session.setAttribute("userDetails", user);

        when(userRepository.existsById(userId)).thenReturn(true);
        assertTrue(userRepository.existsById(userId));

        when(taskRepository.findAllByStatus(Status.PENDING))
                .thenReturn(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).toList());

        assertEquals(tasks.stream().filter(x -> x.getUser().getId().equals(userId)).count(),
                taskService.viewTasksByStatus(Status.PENDING).getData().size());
    }
}