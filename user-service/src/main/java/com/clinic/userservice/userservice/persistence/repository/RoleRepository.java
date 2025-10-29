package com.clinic.userservice.userservice.persistence.repository;

import com.clinic.commonservice.enums.RoleName;
import com.clinic.userservice.userservice.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository interface for Role entity.
 * Extends JpaRepository to provide CRUD operations.
 *
 * @author caito
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleName rol);
}
