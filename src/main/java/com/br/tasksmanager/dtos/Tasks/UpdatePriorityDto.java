package com.br.tasksmanager.dtos.Tasks;

import com.br.tasksmanager.Enums.TaskPriority;
import jakarta.validation.constraints.NotNull;

public record UpdatePriorityDto(@NotNull(message = "The priority cannot be null") TaskPriority priority) {
}
