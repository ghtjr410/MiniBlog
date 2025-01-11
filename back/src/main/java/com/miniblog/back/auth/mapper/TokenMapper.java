package com.miniblog.back.auth.mapper;

import com.miniblog.back.auth.model.Token;
import com.miniblog.back.member.model.Member;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TokenMapper {

    public Token create(Member member, String refreshToken, LocalDateTime expiresDate, String deviceInfo) {
        return Token.builder()
                .member(member)
                .refreshToken(refreshToken)
                .expiresDate(expiresDate)
                .deviceInfo(deviceInfo)
                .build();
    }
}
