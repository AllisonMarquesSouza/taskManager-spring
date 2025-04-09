package com.br.tasksmanager.dtos.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank(message = "The username can't be blank")
        String username,
        @NotBlank(message = "The password can't be blank")
        String password,
        @NotBlank(message = "The email can't be blank")
        @Email(message = "The email must be valid")
        String email ) {
}
