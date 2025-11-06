package com.clinic.authservice.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * loginRequest class represents the request model for user login.
 * It includes the user's email and password.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class LoginRequest implements Serializable {
    private String email;
    private String password;
}
