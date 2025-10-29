package com.clinic.userservice.userservice.persistence.repository;

import com.clinic.userservice.userservice.persistence.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * Repository interface for UserApp entity.
 * Extends JpaRepository to provide CRUD operations.
 *
 * @author caito
 *
 */
public interface UserRepository extends JpaRepository<UserApp, Long> {
    Optional<UserApp> findByEmail(String email);
    boolean existsByEmail(String email);
    List<UserApp> findByFullNameContainingIgnoreCase(String name);
    boolean existsByDni(String dni);
}
