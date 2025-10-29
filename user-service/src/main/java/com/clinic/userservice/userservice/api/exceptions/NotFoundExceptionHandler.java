package com.clinic.userservice.userservice.api.exceptions;

import com.clinic.commonservice.exceptions.NotFoundException;
import com.clinic.commonservice.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for NotFoundException.
 * This class captures NotFoundException thrown in the application
 * and returns a structured error response with HTTP status 404.
 *
 * @author caito
 *
 */
@RestControllerAdvice
public class NotFoundExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> notFoundHandler(NotFoundException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .code(HttpStatus.NOT_FOUND.value())
                                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                                .timestamp(LocalDateTime.now())
                                .message(e.getMessage())
                                .method(request.getMethod())
                                .path(request.getRequestURL().toString())
                                .build()
                );
    }
}
