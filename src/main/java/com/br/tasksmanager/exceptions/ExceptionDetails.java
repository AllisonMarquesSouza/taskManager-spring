package com.br.tasksmanager.exceptions;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ExceptionDetails {
    private String title;
    private String message;
    protected int status;
    protected String details;
    protected LocalDateTime timestamp;
}