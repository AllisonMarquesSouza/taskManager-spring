package com.br.tasksmanager.dtos.Tasks;

import jakarta.validation.constraints.NotBlank;

public record UpdateNameDto(@NotBlank(message = "The name cannot be blank, check it") String name) { }
