package com.clinic.userservice.userservice.api.controllers.impl;

import com.clinic.commonservice.exceptions.NotFoundException;
import com.clinic.commonservice.logs.WriteLog;
import com.clinic.userservice.userservice.api.controllers.contracts.UserController;
import com.clinic.userservice.userservice.api.models.requests.UserEnabledRequest;
import com.clinic.userservice.userservice.api.models.requests.UserRequest;
import com.clinic.userservice.userservice.api.models.responses.UserFullDataResponse;
import com.clinic.userservice.userservice.api.models.responses.UserResponse;
import com.clinic.userservice.userservice.services.contracts.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserControlerImpl class implements the UserController interface.
 * It provides the implementation for user-related operations.
 * This class is annotated as a REST controller and uses constructor injection for the UserService.
 *
 * @author caito
 *
 */
@Slf4j
@RestController
@RequestMapping("v1/clinical/users")
@RequiredArgsConstructor
@Tag(name = "User Controller", description = "Endpoints for managing users")
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class UserControlerImpl implements UserController {
    private final UserService userService;

    @Override
    public ResponseEntity<?> createUser(UserRequest request) {
        userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Page<UserResponse>> getAllUsers(int page, int size) {
        Page<UserResponse> users = userService.getUsers(page, size);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<List<UserResponse>> getUsersByName(String name) {
        var users = userService.getUsers(name);
        if (users.isEmpty()){
            log.error(WriteLog.logError("No users found with name: " + name));
            throw new NotFoundException("No users found with name: " + name);
        }
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserResponse> getUsersByEmail(String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @Override
    public ResponseEntity<UserResponse> getUsersByDni(String dni) {
        return ResponseEntity.ok(userService.getUserByDni(dni));
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(Long id, UserRequest request) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<?> enableUser(UserEnabledRequest request) {
        userService.enabledUser(request);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserFullDataResponse> getFullUserData(String email) {
        return ResponseEntity.ok(userService.getUserFulData(email));
    }

    @Override
    public ResponseEntity<UserFullDataResponse> activationRequest(String email) {
        userService.activationRequest(email);
        return ResponseEntity.ok().build();
    }


}
