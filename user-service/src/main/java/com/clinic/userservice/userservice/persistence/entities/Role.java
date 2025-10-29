package com.clinic.userservice.userservice.persistence.entities;

import com.clinic.commonservice.enums.RoleName;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

/*
 * Role entity representing a user role in the system.
 * Implements GrantedAuthority for Spring Security integration.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "roles")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Override
    public String getAuthority() {
        return role.name();
    }
}
