package com.clinic.userservice.userservice.services.contracts;

import com.clinic.userservice.userservice.api.models.requests.UserEnabledRequest;
import com.clinic.userservice.userservice.api.models.requests.UserRequest;
import com.clinic.userservice.userservice.api.models.responses.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * UserService interface defines the contract for user-related operations
 * in the User Service of the Clinic application.
 *
 * @author caito
 *
 */
public interface UserService {

    void createUser(UserRequest request);
    Page<UserResponse> getUsers(int page, int size);
    List<UserResponse> getUsers(String name);
    UserResponse getUserByEmail(String email);
    UserResponse getUserByDni(String dni);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    void enabledUser(UserEnabledRequest request);
}
