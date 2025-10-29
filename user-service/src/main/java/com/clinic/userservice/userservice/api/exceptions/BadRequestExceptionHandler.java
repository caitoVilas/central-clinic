package com.clinic.userservice.userservice.api.exceptions;

import com.clinic.commonservice.exceptions.BadRequestException;
import com.clinic.commonservice.models.ErrorsResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for BadRequestException.
 * This class captures BadRequestException thrown in the application
 * and returns a structured error response with HTTP status 400.
 *
 * @author caito
 *
 */
@RestControllerAdvice
public class BadRequestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorsResponse> badRequestHandler(BadRequestException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorsResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .timestamp(LocalDateTime.now())
                                .messages(e.getMessages())
                                .method(request.getMethod())
                                .path(request.getRequestURL().toString())
                                .build()
                );
    }
}
