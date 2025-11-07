package com.clinic.userservice.userservice.persistence.repository;

import com.clinic.userservice.userservice.persistence.entities.ValidationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository interface for managing ValidationToken entities.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 *
 * @author caito
 *
 */
public interface ValidationTokenRepository extends JpaRepository<ValidationToken, Long> {
    Optional<ValidationToken> findByToken(String token);
    void deleteAllByEmail(String email);
}
