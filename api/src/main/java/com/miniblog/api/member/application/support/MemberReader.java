package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.member.application.port.MemberRepository;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.MemberErrorMessage.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReader {

    private final MemberRepository memberRepository;

    /**
     * ID로 회원 조회
     */
    public Member getById(long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    /**
     * 사용자명으로 회원 조회
     */
    public Member getByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }

    /**
     * 이메일로 회원 조회
     */
    public Member getByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(MEMBER_NOT_FOUND));
    }
}
