package com.br.tasksmanager.util;

import com.br.tasksmanager.Enums.TaskPriority;
import com.br.tasksmanager.Enums.TaskStatus;
import com.br.tasksmanager.dtos.Tasks.TaskRequestDto;
import com.br.tasksmanager.models.Tasks;
import com.br.tasksmanager.models.Users;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class TasksCreate {

    public static Tasks createTask(Users user){
        return Tasks.builder()
                .nameTask("Study english")
                .user(user)
                .description("read and have a conversation in english")
                .status(TaskStatus.COMPLETED)
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.of(2026, 5, 20))
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

    }
    public static Tasks createTaskService(Users user){
        return Tasks.builder()
                .id(UUID.randomUUID())
                .nameTask("Study english")
                .user(user)
                .description("read and have a conversation in english")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.of(2026, 5, 20))
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

    }
    public static Tasks createTask2(Users user) {
        return Tasks.builder()
                .nameTask("Learn Docker")
                .user(user)
                .description("Study Docker and create containers")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDate.of(2026, 6, 10))
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();
    }
    public static TaskRequestDto createTaskDto(Long userId){
        return TaskRequestDto.builder()
                .nameTask("Exercise")
                .userId(userId)
                .description("Go for a 30-minute run")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.of(2025, 5, 10))
                .build();
    }

}
