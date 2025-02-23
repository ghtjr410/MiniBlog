package com.miniblog.api.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Builder
@Getter
@AllArgsConstructor(access = PRIVATE)
public class BlacklistToken {

    private final String token;
    private final LocalDateTime expiresDate;

    public static BlacklistToken create(String token, LocalDateTime expiresDate) {
        return BlacklistToken.builder()
                .token(token)
                .expiresDate(expiresDate)
                .build();
    }

    public long getTtlInSeconds(LocalDateTime now) {
        return Duration.between(now, expiresDate).getSeconds();
    }
}
