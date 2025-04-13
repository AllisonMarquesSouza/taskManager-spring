package com.br.tasksmanager.dtos.Tasks;

import com.br.tasksmanager.Enums.TaskPriority;
import com.br.tasksmanager.Enums.TaskStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
@Builder
public record TaskRequestDto(
        @NotNull(message = "The userId can't be null")
        Long userId,

        @NotBlank(message = "The description can't be blank")
        String nameTask,

        @NotBlank(message = "The description can't be blank")
        String description,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Type priority can't be null")
        TaskPriority priority,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Type status can't be null")
        TaskStatus status,

        @NotNull(message = "The Due date can't be null")
        LocalDate dueDate
)
{}
