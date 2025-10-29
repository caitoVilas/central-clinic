package com.clinic.userservice.userservice.utils.mappers;

import com.clinic.userservice.userservice.api.models.responses.RoleResponse;
import com.clinic.userservice.userservice.persistence.entities.Role;

/*
 * RoleMapper class for mapping Role entities to RoleResponse DTOs.
 *
 * @author caito
 *
 */
public class RoleMapper {

    /*
     * Maps a Role entity to a RoleResponse DTO.
     *
     * @param role the Role entity to be mapped
     * @return the corresponding RoleResponse DTO
     */
    public static RoleResponse mapToDto(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .rol(role.getRole())
                .build();
    }
}
