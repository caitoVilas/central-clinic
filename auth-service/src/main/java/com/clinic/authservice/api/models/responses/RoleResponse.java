package com.clinic.authservice.api.models.responses;

import com.clinic.commonservice.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * RoleResponse class represents the response model for a role.
 * It includes the role ID and role name.
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
