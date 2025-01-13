package com.miniblog.back.member.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "email_verification",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})}
)
public class EmailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "code", nullable = false, length = 4)
    private String code;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "expires_date", nullable = false)
    private LocalDateTime expiresDate;
}
