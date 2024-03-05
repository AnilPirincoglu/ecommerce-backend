package com.challenge.backend.ecommerce.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public ResponseEntity<ExceptionResponse> exceptionHandler(ConstraintViolationException notValidException) {
        var status = HttpStatus.BAD_REQUEST;

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                status.value(),
                status.name(),
                notValidException.getConstraintViolations()
                        .stream()
                        .findFirst()
                        .get()
                        .getMessage(),
                Instant.now());

        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> exceptionHandler(UsernameNotFoundException userNotFound){
        var status=HttpStatus.NOT_FOUND;
        ExceptionResponse exceptionResponse = new ExceptionResponse(status.value(),status.name(),userNotFound.getMessage(),Instant.now());
        return new ResponseEntity<>(exceptionResponse,status);
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
