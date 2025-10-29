package com.clinic.userservice.userservice.api.models.requests;

import com.clinic.commonservice.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * UserRequest model representing the data required to create or update a user.
 * Implements Serializable for object serialization.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class UserRequest implements Serializable {
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
    private RoleName role;
}
