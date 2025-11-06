package com.clinic.userservice.userservice.utils.mappers;

import com.clinic.userservice.userservice.api.models.responses.UserFullDataResponse;
import com.clinic.userservice.userservice.persistence.entities.UserApp;

import java.util.stream.Collectors;

/**
 * FullUserMapper is a utility class that provides methods to map UserApp entities to UserFullDataResponse DTOs.
 * It contains static methods for converting between these representations, facilitating the transfer of user data
 * between different layers of the application. This class helps in separating the mapping logic from the business logic,
 * promoting cleaner code.
 *
 * @author caito
 *
 */
public class FullUserMapper {

    /** Maps a UserApp entity to a UserFullDataResponse DTO.
     *
     * @param user the UserApp entity to be mapped
     * @return the corresponding UserFullDataResponse DTO
     */
    public static UserFullDataResponse mapToDto(UserApp user){
        return UserFullDataResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phone(user.getPhone())
                .gender(user.getGender())
                .dni(user.getDni())
                .tuition(user.getTuition())
                .socialWork(user.getSocialWork())
                .membershipNumber(user.getMembershipNumber())
                .plan(user.getPlan())
                .imageUrl(user.getImageUrl())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream().map(RoleMapper::mapToDto).collect(Collectors.toSet()))
                .build();
    }
}
