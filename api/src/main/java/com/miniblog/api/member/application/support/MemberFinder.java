package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.member.application.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.MemberErrorMessage.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFinder {

    private final MemberRepository memberRepository;

    /**
     * ID로 회원 존재 여부 확인
     */
    public void ensureExistsById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException(MEMBER_NOT_FOUND);
        }
    }

    /**
     * 사용자명 존재 여부 확인
     */
    public boolean existsByUsername(String username) {
        return memberRepository.existsByUsername(username);
    }

    /**
     * 닉네임 존재 여부 확인
     */
    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 이메일 존재 여부 확인
     */
    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
}
