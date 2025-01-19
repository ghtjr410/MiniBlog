package com.miniblog.back.member.validator;

import com.miniblog.back.common.exception.DuplicateException;
import com.miniblog.back.member.dto.request.RegisterRequestDTO;
import com.miniblog.back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateUsername(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateException("이미 존재하는 아이디입니다.");
        }
    }
    public void validateNickname(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateException("이미 존재하는 닉네임입니다.");
        }
    }

    public void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateException("이미 존재하는 이메일입니다.");
        }
    }

    public void validateForRegistration(String username, String nickname, String email) {
        validateUsername(username);
        validateNickname(nickname);
        validateEmail(email);
    }
}
