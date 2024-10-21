package dev.anilp.ecommerce_backend.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        String errorName,
        String path,
        int statusCode,
        LocalDateTime timestamp,
        List<String> errors
) {
}
