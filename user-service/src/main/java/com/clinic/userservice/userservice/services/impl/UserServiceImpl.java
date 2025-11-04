package com.clinic.userservice.userservice.services.impl;

import com.clinic.commonservice.exceptions.BadRequestException;
import com.clinic.commonservice.exceptions.BrokerMsgException;
import com.clinic.commonservice.exceptions.NotFoundException;
import com.clinic.commonservice.helpers.ValidationHelper;
import com.clinic.commonservice.logs.WriteLog;
import com.clinic.commonservice.models.RegisterUser;
import com.clinic.userservice.userservice.api.models.requests.UserRequest;
import com.clinic.userservice.userservice.api.models.responses.UserResponse;
import com.clinic.userservice.userservice.persistence.entities.Role;
import com.clinic.userservice.userservice.persistence.entities.UserApp;
import com.clinic.userservice.userservice.persistence.entities.ValidationToken;
import com.clinic.userservice.userservice.persistence.repository.RoleRepository;
import com.clinic.userservice.userservice.persistence.repository.UserRepository;
import com.clinic.userservice.userservice.persistence.repository.ValidationTokenRepository;
import com.clinic.userservice.userservice.services.contracts.UserService;
import com.clinic.userservice.userservice.utils.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the UserService interface.
 * This service handles user-related operations such as creating new users.
 *
 * @author caito
 *
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ValidationTokenRepository validationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, RegisterUser> userTemplate;

    /**
     * Creates a new user based on the provided UserRequest.
     *
     * @param request the user request containing user details
     * @throws BadRequestException if validation fails
     * @throws NotFoundException if the specified role is not found
     */
    @Override
    @Transactional
    public void createUser(UserRequest request) {
        log.info(WriteLog.logInfo("--> Creating new user service"));
        validateUser(request);
        Set<Role> roles = new HashSet<>();
        UserApp user = UserMapper.mapToEntity(request);
        Role userRole = roleRepository.findByRole(request.getRole())
                .orElseThrow(() -> {
                    log.error(WriteLog.logError("Role not found: " + request.getRole()));
                    return new NotFoundException("Role not found: " + request.getRole());
                });
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        log.info(WriteLog.logInfo("--> new user created"));
        log.info(WriteLog.logInfo("--> generate validation token..."));
        ValidationToken vt = generateValidationToken(request.getEmail());
        validationTokenRepository.save(vt);
        log.info(WriteLog.logInfo("--> send message to broker..."));
        CompletableFuture<SendResult<String, RegisterUser>> future = userTemplate.send(
                "userTopic",
                RegisterUser.builder()
                        .email(request.getEmail())
                        .username(request.getFullName())
                        .validationToken(vt.getToken())
                        .build()
        );
        future.whenCompleteAsync((r,t) -> {
            if (t != null){
                log.error(WriteLog.logError("Error sending message to broker: " + t.getMessage()));
                throw new BrokerMsgException("Error sending message to broker: " + t.getMessage());
            } else {
                log.info(WriteLog.logInfo("Message sent to broker successfully " ));
            }
        });
    }



    /**
     * Retrieves a paginated list of users.
     *
     * @param page the page number to retrieve
     * @param size the number of users per page
     * @return a paginated list of UserResponse objects
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getUsers(int page, int size) {
        log.info(WriteLog.logInfo("--> Getting users service"));
        var pr = PageRequest.of(page, size);
        return userRepository.findAll(pr).map(UserMapper::mapToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsers(String name) {
        log.info(WriteLog.logInfo("--> Getting users by name service"));
        return userRepository.findByFullNameContainingIgnoreCase(name).stream().map(UserMapper::mapToDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        log.info(WriteLog.logInfo("--> Getting user by email service"));
        return UserMapper.mapToDto(userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User not found with email: " + email));
                    return new NotFoundException("User not found with email: " + email);
                }
        ));
    }

    @Override
    public UserResponse getUserByDni(String dni) {
        log.info(WriteLog.logInfo("--> Getting user by DNI service"));
        return UserMapper.mapToDto(userRepository.findByDni(dni).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User not found with DNI: " + dni));
                    return new NotFoundException("User not found with DNI: " + dni);
                }
        ));
    }

    /**
     * Updates an existing user with the provided UserRequest data.
     *
     * @param id      the ID of the user to update
     * @param request the user request containing updated user details
     * @return the updated UserResponse object
     * @throws NotFoundException   if the user is not found
     * @throws BadRequestException if validation fails
     */
    @Override
    @Transactional
    public UserResponse updateUser(Long id, UserRequest request) {
        log.info(WriteLog.logInfo("--> Updating user service"));
        var user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User not found with id: " + id));
                    return new NotFoundException("User not found with id: " + id);
                }
        );
        if(request.getFullName() != null && !request.getFullName().isEmpty()) {
            user.setFullName(request.getFullName());
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            if (!ValidationHelper.validateEmail(request.getEmail())) {
                log.error(WriteLog.logError("Invalid email"));
                throw new BadRequestException(List.of("Invalid email"));
            }
            if (userRepository.getByEmailAndIdNoEqual(request.getEmail(), id)) {
                log.error(WriteLog.logError("Email already exists"));
                throw new BadRequestException(List.of("Email already exists"));
            }
            user.setEmail(request.getEmail());
        }
        if (request.getAddress() != null && !request.getAddress().isEmpty()) {
            user.setAddress(request.getAddress());
        }
        if (request.getPhone() != null && !request.getPhone().isEmpty()) {
            user.setPhone(request.getPhone());
        }
        if (request.getGender() != null && !request.getGender().isEmpty()) {
            user.setGender(request.getGender());
        }
        if (request.getDni() != null && !request.getDni().isEmpty()) {
            user.setDni(request.getDni());
        }
        if (request.getTuition() != null && !request.getTuition().isEmpty()) {
            user.setTuition(request.getTuition());
        }
        if (request.getSocialWork() != null && !request.getSocialWork().isEmpty()) {
            user.setSocialWork(request.getSocialWork());
        }
        if (request.getMembershipNumber() != null && !request.getMembershipNumber().isEmpty()) {
            user.setMembershipNumber(request.getMembershipNumber());
        }
        if (request.getPlan() != null && !request.getPlan().isEmpty()) {
            user.setPlan(request.getPlan());
        }
        return UserMapper.mapToDto(userRepository.save(user));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     * @throws NotFoundException if the user is not found
     */
    @Override
    @Transactional
    public void deleteUser(Long id) {
        log.info(WriteLog.logInfo("--> Deleting user service"));
        var user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error(WriteLog.logError("User not found with id: " + id));
                    throw new NotFoundException("User not found with id: " + id);
                }
        );
        userRepository.delete(user);
    }

    /**
     * Validates the user request data.
     *
     * @param request the user request to validate
     * @throws BadRequestException if validation fails with a list of errors
     */
    private void validateUser(UserRequest request) {
        log.info(WriteLog.logInfo("--> Validating user..."));
        List<String> errors = new ArrayList<>();
        if (request.getFullName() == null || request.getFullName().isEmpty()) {
            errors.add("Full name is required.");
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            errors.add("Email is required.");
        } else if (userRepository.existsByEmail(request.getEmail())) {
            errors.add("Email already exists.");
        } else if (!ValidationHelper.validateEmail(request.getEmail())) {
            errors.add("Invalid email format.");
        }
        if (request.getAddress() == null || request.getAddress().isEmpty()) {
            errors.add("Address is required.");
        }
        if (request.getPhone() == null || request.getPhone().isEmpty()) {
            errors.add("Phone number is required.");
        }
        if (request.getGender() == null || request.getGender().isEmpty()) {
            errors.add("Gender is required.");
        }
        if (request.getDni() == null || request.getDni().isEmpty()) {
            errors.add("DNI is required.");
        }
        if (!errors.isEmpty()) {
            log.error(WriteLog.logError("User validation failed: " + String.join(", ", errors)));
            throw new BadRequestException(errors);
        }
    }

    private ValidationToken generateValidationToken(String email) {
        final int MIN = 100000;
        final int MAX = 999999;
        SecureRandom secureRandom = new SecureRandom();
        int token = secureRandom.nextInt((MAX - MIN) + 1) + MIN;
        return ValidationToken.builder()
                .token(String.valueOf(token))
                .email(email)
                .expiryDate(LocalDateTime.now().plusDays(1))
                .build();
    }
}
