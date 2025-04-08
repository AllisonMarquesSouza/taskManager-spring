package com.br.tasksmanager.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
        @NotBlank(message = "The username can't be blank") String username,
        @NotBlank(message = "The password can't be blank")String password )
{}
