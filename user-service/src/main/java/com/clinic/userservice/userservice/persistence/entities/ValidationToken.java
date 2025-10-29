package com.clinic.userservice.userservice.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 * ValidationToken entity representing a token used for validating user actions such as email verification or password reset.
 * Includes fields for token value, associated email, and expiry date.
 * Uses Lombok annotations for boilerplate code reduction.
 *
 * @author caito
 *
 */
@Entity
@Table(name = "validation_tokens")
@NoArgsConstructor@AllArgsConstructor
@Getter@Setter@Builder
public class ValidationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String email;
    private LocalDateTime expiryDate;
}
