package com.challenge.backend.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(GlobalException globalException) {
        var status = globalException.getHttpStatus();

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                status.value(), status.name(),
                globalException.getMessage(), Instant.now());

        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(Exception exception) {
        var status = HttpStatus.BAD_REQUEST;

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                status.value(), status.name(),
                exception.getMessage(), Instant.now());

        return new ResponseEntity<>(exceptionResponse, status);
    }
}
