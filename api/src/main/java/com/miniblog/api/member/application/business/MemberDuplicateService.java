package com.miniblog.api.member.application.business;

import com.miniblog.api.common.domain.exception.ResourceDuplicateException;
import com.miniblog.api.member.application.support.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.MemberErrorMessage.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberDuplicateService {

    private final MemberFinder memberFinder;

    /**
     * 사용자명 중복 검사
     */
    public void ensureNonDuplicateUsername(String username) {
        if (memberFinder.existsByUsername(username)) {
            throw new ResourceDuplicateException(USERNAME_DUPLICATE);
        }
    }

    /**
     * 닉네임 중복 검사
     */
    public void ensureNonDuplicateNickname(String nickname) {
        if (memberFinder.existsByNickname(nickname)) {
            throw new ResourceDuplicateException(NICKNAME_DUPLICATE);
        }
    }

    /**
     * 이메일 중복 검사
     */
    public void ensureNonDuplicateEmail(String email) {
        if (memberFinder.existsByEmail(email)) {
            throw new ResourceDuplicateException(EMAIL_DUPLICATE);
        }
    }

    /**
     * 회원가입 시 중복 검사
     */
    public void ensureSignUpNonDuplicate(String username, String nickname, String email) {
        ensureNonDuplicateUsername(username);
        ensureNonDuplicateNickname(nickname);
        ensureNonDuplicateEmail(email);
    }
}
