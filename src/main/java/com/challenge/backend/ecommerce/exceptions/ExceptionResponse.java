package com.challenge.backend.ecommerce.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
@AllArgsConstructor
@Data
public class ExceptionResponse {
    private Integer errorCode;
    private String errorStatus;
    private String message;
    private Instant timeStamp;
}
