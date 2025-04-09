package com.br.tasksmanager.dtos.Tasks;

import jakarta.validation.constraints.NotBlank;

public record UpdateDescriptionDto(@NotBlank(message = "The description cannot be blank, check it") String description) { }
