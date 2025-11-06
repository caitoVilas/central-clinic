package com.clinic.userservice.userservice.api.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * UserFullDataResponse class represents the full response model for a user.
 * It includes various user attributes such as id, fullName, email, and account status flags.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserFullDataResponse implements Serializable {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String gender;
    private String dni;
    private String tuition;
    private String socialWork;
    private String membershipNumber;
    private String plan;
    private String imageUrl;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Set<RoleResponse> roles;
}
