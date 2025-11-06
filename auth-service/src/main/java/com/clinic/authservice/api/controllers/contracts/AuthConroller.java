package com.clinic.authservice.api.controllers.contracts;


import com.clinic.authservice.api.models.requests.LoginRequest;
import com.clinic.authservice.api.models.responses.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AuthController interface defines the contract for authentication-related endpoints.
 * It includes a method for user login.
 *
 * @author caito
 *
 */
public interface AuthConroller {
    @PostMapping("/login")
    @Operation(summary = "Login user",
            description = "This endpoint allows a user to log in by providing their email and password. " +
                    "It returns a JWT token if the login is successful.")
    @Parameter(name = "request", description = "Login request containing email and password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login successful, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, invalid credentials"),
            @ApiResponse(responseCode = "404", description = "Not found, user does not exist"),
            @ApiResponse(responseCode = "500", description = "Internal server error, unexpected error occurred")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request);
}
