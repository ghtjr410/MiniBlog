package com.miniblog.back.member.model;

import com.miniblog.back.member.listener.EmailVerificationListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(EmailVerificationListener.class)
@Table(
        name = "email_verification",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "email"})},
        indexes = {
                @Index(name = "idx_username_email", columnList = "username, email"),
                @Index(name = "idx_username_email_code", columnList = "username, email, code")
        }
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

    @Column(name = "expires_date", nullable = false)
    private LocalDateTime expiresDate;
}
