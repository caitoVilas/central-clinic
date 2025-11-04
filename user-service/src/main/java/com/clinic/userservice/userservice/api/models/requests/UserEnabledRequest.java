package com.clinic.userservice.userservice.api.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserEnabledRequest represents a request to enable a user account.
 * It contains the necessary information such as password, confirmPassword, and token.
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserEnabledRequest implements Serializable {
    private String password;
    private String confirmPassword;
    private String token;
}
