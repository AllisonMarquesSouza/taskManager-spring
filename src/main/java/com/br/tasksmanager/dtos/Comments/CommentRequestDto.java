package com.br.tasksmanager.dtos.Comments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CommentRequestDto (
        @NotNull(message = "The user id cannot be null")
        Long userId,
        @NotNull(message = "The task id cannot be null")
        UUID taskId,
        @NotBlank(message = "The Comment cannot be blank")
        String comment
) {}
