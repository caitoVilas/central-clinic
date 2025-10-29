package com.clinic.userservice.userservice.api.models.responses;


import com.clinic.commonservice.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * RoleResponse model representing the data returned for a role.
 * Implements Serializable for object serialization.
 *
 * @author caito
 *
 */
@NoArgsConstructor@AllArgsConstructor
@Data@Builder
public class RoleResponse implements Serializable {
    private Long id;
    private RoleName rol;
}
