package dev.anilp.ecommerce_backend.exception;

import dev.anilp.ecommerce_backend.exception.exception_class.DuplicateResourceException;
import dev.anilp.ecommerce_backend.exception.exception_class.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handle(DuplicateResourceException e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e, HttpServletRequest request) {
        LOG.error("Error Name: {},Error Message: {}, Stack-Trace:\n{}",
                e.getClass().getSimpleName(), e.getMessage(), getStackTraceAsString(e));

        List<String> errors = e.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorResponse errorResponse = createErrorResponse(e, request, HttpStatus.BAD_REQUEST, errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e, HttpServletRequest request) {
        return handleException(e, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request, HttpStatus httpStatus) {
        LOG.error("Error Name: {}, Error Message: {}, Stack-Trace:\n{}",
                e.getClass().getSimpleName(), e.getMessage(), getStackTraceAsString(e));
        List<String> errors = List.of(e.getMessage());
        ErrorResponse errorResponse = createErrorResponse(e, request, httpStatus, errors);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private String getStackTraceAsString(Throwable throwable) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return sw.toString();
    }

    private ErrorResponse createErrorResponse(Exception e, HttpServletRequest request, HttpStatus httpStatus, List<String> errors) {
        return new ErrorResponse(
                e.getClass().getSimpleName(),
                request.getRequestURI(),
                httpStatus.value(),
                LocalDateTime.now(),
                errors
        );
    }
}
