package com.clinic.userservice.userservice.configs.security.filters;

import com.clinic.commonservice.logs.WriteLog;
import com.clinic.commonservice.models.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Entry point for handling unauthorized access attempts.
 * Implements AuthenticationEntryPoint to manage authentication exceptions.
 * Constructs and sends a structured JSON error response for unauthorized requests.
 * Logs the unauthorized access attempts.
 *
 * @author : caito
 *
 */
@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        final String MSG = "Unauthorized resource access";
        var res = ErrorResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .message(MSG)
                .method(request.getMethod())
                .path(request.getRequestURL().toString())
                .build();
        log.error(WriteLog.logError("--> " + MSG));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String apiError = mapper.writeValueAsString(res);
        response.getWriter().write(apiError);
    }
}
