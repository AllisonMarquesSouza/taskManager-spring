package com.br.tasksmanager.dtos.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AuthenticationDto(
        @NotBlank(message = "The username can't be blank") String username,
        @NotBlank(message = "The password can't be blank")String password )
{}
