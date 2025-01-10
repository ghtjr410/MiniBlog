package com.miniblog.back.mapper;

import com.miniblog.back.dto.member.RegisterRequest;
import com.miniblog.back.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final PasswordEncoder passwordEncoder;

    public Member create(RegisterRequest request) {
        return Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .nickname(request.nickname())
                .build();
    }
}
