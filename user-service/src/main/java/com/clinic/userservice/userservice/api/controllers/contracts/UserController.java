package com.clinic.userservice.userservice.api.controllers.contracts;

import com.clinic.userservice.userservice.api.models.requests.UserEnabledRequest;
import com.clinic.userservice.userservice.api.models.requests.UserRequest;
import com.clinic.userservice.userservice.api.models.responses.UserFullDataResponse;
import com.clinic.userservice.userservice.api.models.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * UserController interface defines the contract for user-related operations.
 * It includes methods for creating a new user.
 * Each method is annotated with OpenAPI annotations for documentation and security requirements.
 *
 * @author caito
 *
 */
public interface UserController {
    @PostMapping("/create")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Add a new user")
    @Parameter(name = "request", description = "user request object containing details of the user to be added")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "user added successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> createUser(@RequestBody UserRequest request);

    @GetMapping
    @SecurityRequirement(name = "security token")
    @Operation(description = "Retrieve all users")
    @Parameters({
            @Parameter(name = "page", description = "Page number for pagination", example = "0"),
            @Parameter(name = "size", description = "Number of users per page", example = "10")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "204", description = "No content"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size);

    @GetMapping("/by-name/{name}")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Retrieve users by name")
    @Parameter(name = "name", description = "name of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<UserResponse>> getUsersByName(@PathVariable String name);

    @GetMapping("/by-email/{email}")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Retrieve users by email")
    @Parameter(name = "email", description = "email of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> getUsersByEmail(@PathVariable String email);

    @GetMapping("/by-dni/{dni}")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Retrieve users by dni")
    @Parameter(name = "dni", description = "dni of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> getUsersByDni(@PathVariable String dni);

    @PutMapping("/update/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Update an existing user")
    @Parameter(name = "id", description = "ID of the user to update")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users updated successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request);

    @DeleteMapping("/delete/{id}")
    @SecurityRequirement(name = "security token")
    @Operation(description = "Delete an existing user")
    @Parameter(name = "id", description = "ID of the user to delete")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "user deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> deleteUser(@PathVariable Long id);

    @PutMapping("/enabled")
    @Operation(description = "Enable and set password for an user")
    @Parameter(name = "request", description = "" +
            "user enabled request object containing details of the user to be enabled")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "user enabled successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> enableUser(@RequestBody UserEnabledRequest request);

    @GetMapping("/full-data/{email}")
    @SecurityRequirement(name = "security token")
    @Operation(hidden = true)
    @Parameter(name = "email", description = "email of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserFullDataResponse> getFullUserData(@PathVariable String email);

    @GetMapping("/activation-request/{email}")
    @Operation(description = "Request account activation for an user")
    @Parameter(name = "email", description = "email of user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "users retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserFullDataResponse> activationRequest(@PathVariable String email);
}

