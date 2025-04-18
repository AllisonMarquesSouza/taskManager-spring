package com.br.tasksmanager.dtos.Comments;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CommentUpdateDto(
        @NotBlank(message = "The comment cannot be blank")
        String comment
) {}
