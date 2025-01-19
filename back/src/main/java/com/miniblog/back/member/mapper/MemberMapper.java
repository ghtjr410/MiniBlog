package com.miniblog.back.member.mapper;

import com.miniblog.back.member.dto.request.RegisterRequestDTO;
import com.miniblog.back.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final PasswordEncoder passwordEncoder;

    public Member create(RegisterRequestDTO request) {
        return Member.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .email(request.email())
                .nickname(request.nickname())
                .build();
    }
}
