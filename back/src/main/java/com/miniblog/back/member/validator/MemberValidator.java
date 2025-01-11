package com.miniblog.back.member.validator;

import com.miniblog.back.member.dto.request.RegisterRequest;
import com.miniblog.back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
    }

    public void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
    }

    public void validateForRegistration(RegisterRequest request) {
        validateUsername(request.username());
        validateEmail(request.email());
    }
}
