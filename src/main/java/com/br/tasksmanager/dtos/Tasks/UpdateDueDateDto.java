package com.br.tasksmanager.dtos.Tasks;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateDueDateDto(@NotNull(message = "The due date cannot be null")LocalDate dueDate) {
}
