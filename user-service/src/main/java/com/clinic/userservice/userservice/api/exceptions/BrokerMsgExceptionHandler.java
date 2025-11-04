package com.clinic.userservice.userservice.api.exceptions;

import com.clinic.commonservice.exceptions.BrokerMsgException;
import com.clinic.commonservice.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for BrokerMsgException.
 * This class captures BrokerMsgException thrown in the application
 * and returns a structured error response with HTTP status 503.
 *
 * @author caito
 *
 */
@RestControllerAdvice
public class BrokerMsgExceptionHandler {
    @ExceptionHandler(BrokerMsgException.class)
    protected ResponseEntity<ErrorResponse> brokenMsgHandler(BrokerMsgException e, HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(
                        ErrorResponse.builder()
                                .code(HttpStatus.SERVICE_UNAVAILABLE.value())
                                .status(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                                .message(e.getMessage())
                                .method(request.getMethod())
                                .path(request.getRequestURL().toString())
                                .build()
                );
    }
}
