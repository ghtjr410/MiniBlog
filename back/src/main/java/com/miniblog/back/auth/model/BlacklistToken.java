package com.miniblog.back.auth.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blacklist_token")
public class BlacklistToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", unique = true, nullable = false)
    private String refreshToken;

    @Column(name = "expires_date", nullable = false)
    private LocalDateTime expiresDate;
}
