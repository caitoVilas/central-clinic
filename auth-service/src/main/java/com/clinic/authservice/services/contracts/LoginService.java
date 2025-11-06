package com.clinic.authservice.services.contracts;

import com.clinic.authservice.api.models.requests.LoginRequest;
import com.clinic.authservice.api.models.responses.LoginResponse;

/**
 * LoginService interface defines the contract for user login operations.
 * It includes a method to authenticate users based on their login request.
 *
 * @author caito
 *
 */
public interface LoginService {
    LoginResponse login(LoginRequest request);
}
