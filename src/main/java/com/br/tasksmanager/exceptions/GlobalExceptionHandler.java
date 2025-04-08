package com.br.tasksmanager.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionDetails> handleEntityNotFoundException(EntityNotFoundException notFound){
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("EntityNotFoundException")
                        .message(notFound.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(notFound.getCause()!= null ? notFound.getCause().toString() : "No details available")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArg) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("MethodArgumentNotValidException")
                        .message(methodArg.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(methodArg.getCause()!= null ? methodArg.getCause().toString() : "No details available")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDetails> handleBadRequestException(BadRequestException bad) {
        return new ResponseEntity<>(
                ExceptionDetails.builder()
                        .title("Bad Request Exception , Check the Documentation")
                        .message(bad.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .details(bad.getCause() != null ? bad.getCause().toString() : "No details available")
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST);
    }
}
