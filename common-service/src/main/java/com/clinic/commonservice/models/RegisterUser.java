package com.clinic.commonservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RegisterUser class represents a user registration message.
 * It contains the email, username, and validation token of the user.
 * This class is used for sending user registration messages in the system.
 *
 * @author caito
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class RegisterUser implements Serializable {
    private String email;
    private String username;
    private String validationToken;
}
