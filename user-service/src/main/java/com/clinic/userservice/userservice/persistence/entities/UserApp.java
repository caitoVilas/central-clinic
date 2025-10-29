package com.clinic.userservice.userservice.persistence.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * UserApp entity representing a user in the system.
 * Implements UserDetails for Spring Security integration.
 * Includes fields for user information and account status.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "users")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class UserApp implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private String gender;
    private String dni;
    private String tuition;
    private String imageUrl;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null) return null;
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getRole().name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return fullName;
    }
}
