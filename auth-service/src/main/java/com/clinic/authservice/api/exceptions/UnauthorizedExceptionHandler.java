package com.clinic.authservice.api.exceptions;

import com.clinic.commonservice.exceptions.UnauthorizedException;
import com.clinic.commonservice.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for UnauthorizedException.
 * This class captures UnauthorizedException thrown in the application
 * and returns a structured error response with HTTP status 401.
 *
 * @author caito
 *
 */
@RestControllerAdvice
public class UnauthorizedExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> unauthizedHandler(com.clinic.commonservice.exceptions.UnauthorizedException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .code(HttpStatus.UNAUTHORIZED.value())
                                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                                .timestamp(LocalDateTime.now())
                                .message(e.getMessage())
                                .method(request.getMethod())
                                .path(request.getRequestURL().toString())
                                .build()
                );
    }
}
