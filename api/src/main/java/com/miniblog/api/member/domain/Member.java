package com.miniblog.api.member.domain;

import com.miniblog.api.member.application.dto.SignupData;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "nickname", unique = true, nullable = false, length = 6)
    private String nickname;

    @Column(name = "role", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_login_date")
    private LocalDateTime lastLoginDate;

    @Column(name = "last_activity_date")
    private LocalDateTime lastActivityDate;


    public static Member create(SignupData data, String encodedPassword, RoleType role, LocalDateTime now) {
        return Member.builder()
                .username(data.username())
                .password(encodedPassword)
                .email(data.email())
                .nickname(data.nickname())
                .role(role)
                .createdDate(now)
                .build();
    }

    public void recordLastLoginDate(LocalDateTime now) {
        this.lastLoginDate = now;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
