package com.miniblog.back.auth.model;

import com.miniblog.back.member.model.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "refresh_token", unique = true, nullable = false)
    private String refreshToken;

    @Column(name = "device_info", nullable = false)
    private String deviceInfo;

    @Column(name = "expires_date", nullable = false)
    private LocalDateTime expiresDate;
}
