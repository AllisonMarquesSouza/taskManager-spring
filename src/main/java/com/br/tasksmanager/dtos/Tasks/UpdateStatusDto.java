package com.br.tasksmanager.dtos.Tasks;

import com.br.tasksmanager.Enums.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateStatusDto(@NotNull(message = "The status cannot be null") TaskStatus status) {
}
