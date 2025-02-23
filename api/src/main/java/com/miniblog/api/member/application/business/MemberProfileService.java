package com.miniblog.api.member.application.business;

import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberProfileService {

    private final MemberReader memberReader;
    private final MemberDuplicateService memberDuplicateChecker;
    private final PasswordEncoder passwordEncoder;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * 닉네임 변경
     */
    public String changeNickname(long id, String nickname) {
        memberDuplicateChecker.ensureNonDuplicateNickname(nickname);
        Member member = memberReader.getById(id);

        member.changeNickname(nickname);

        memberActivityEventPublisher.publishActivity(member.getId());
        return member.getNickname();
    }

    /**
     * 비밀번호 변경
     */
    public void changePassword(long id, String password) {
        Member member = memberReader.getById(id);

        member.changePassword(passwordEncoder.encode(password));

        memberActivityEventPublisher.publishActivity(member.getId());
    }
}
