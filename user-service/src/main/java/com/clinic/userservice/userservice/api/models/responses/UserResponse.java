package com.clinic.userservice.userservice.api.models.responses;

import com.clinic.userservice.userservice.persistence.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/*
 * UserResponse model representing the data returned for a user.
 * Implements Serializable for object serialization.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserResponse implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String address;
    private String phone;
    private String gender;
    private String dni;
    private String tuition;
    private String socialWork;
    private String membershipNumber;
    private String plan;
    private Set<RoleResponse> roles;
}
