package com.clinic.authservice.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * LoginResponse class represents the response model for a login operation.
 * It includes the access token issued upon successful authentication.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class LoginResponse implements Serializable {
    private String access_token;
}
