package com.clinic.authservice.api.controllers.impl;

import com.clinic.authservice.api.controllers.contracts.AuthConroller;
import com.clinic.authservice.api.models.requests.LoginRequest;
import com.clinic.authservice.api.models.responses.LoginResponse;
import com.clinic.authservice.services.contracts.LoginService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthControllerImpl class implements the AuthConroller interface.
 * It provides the endpoint for user authentication.
 *
 * @author caito
 *
 */
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthControllerImpl implements AuthConroller {
    private final LoginService loginService;

    @Override
    public ResponseEntity<LoginResponse> login(LoginRequest request) {
        return ResponseEntity.ok(loginService.login(request));
    }
}
