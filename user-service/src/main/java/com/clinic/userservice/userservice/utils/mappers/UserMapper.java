package com.clinic.userservice.userservice.utils.mappers;

import com.clinic.userservice.userservice.api.models.requests.UserRequest;
import com.clinic.userservice.userservice.api.models.responses.UserResponse;
import com.clinic.userservice.userservice.persistence.entities.UserApp;

import java.util.stream.Collectors;

/**
 * UserMapper is a utility class that provides methods to map UserApp entities to UserResponse DTOs
 * and UserRequest DTOs to UserApp entities. It contains static methods for converting between these
 * representations, facilitating the transfer of user data between different layers of the application.
 * This class helps in separating the mapping logic from the business logic, promoting cleaner code.
 *
 * @author caito
 *
 */
public class UserMapper {

    /** Maps a UserRequest DTO to a UserApp entity.
     *
     * @param request the UserRequest DTO to be mapped
     * @return the corresponding UserApp entity
     */
    public static UserApp mapToEntity(UserRequest request){
        return UserApp.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .address(request.getAddress())
                .phone(request.getPhone())
                .dni(request.getDni())
                .gender(request.getGender())
                .tuition(request.getTuition())
                .socialWork(request.getSocialWork())
                .membershipNumber(request.getMembershipNumber())
                .plan(request.getPlan())
                .build();
    }

    /** Maps a UserApp entity to a UserResponse DTO.
     *
     * @param userApp the UserApp entity to be mapped
     * @return the corresponding UserResponse DTO
     */
    public static UserResponse mapToDto(UserApp userApp) {
        return UserResponse.builder()
                .id(userApp.getId())
                .fullName(userApp.getFullName())
                .email(userApp.getEmail())
                .address(userApp.getAddress())
                .phone(userApp.getPhone())
                .gender(userApp.getGender())
                .dni(userApp.getDni())
                .tuition(userApp.getTuition())
                .socialWork(userApp.getSocialWork())
                .membershipNumber(userApp.getMembershipNumber())
                .plan(userApp.getPlan())
                .roles(userApp.getRoles().stream().map(RoleMapper::mapToDto).collect(Collectors.toSet()))
                .build();
    }
}
