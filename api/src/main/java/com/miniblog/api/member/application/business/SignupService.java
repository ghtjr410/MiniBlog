package com.miniblog.api.member.application.business;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.dto.SignupData;
import com.miniblog.api.member.application.port.MemberRepository;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.RoleType.ROLE_USER;

@Service
@RequiredArgsConstructor
@Transactional
public class SignupService {

    private final MemberDuplicateService memberDuplicateService;
    private final EmailVerificationService emailVerificationService;
    private final PasswordEncoder passwordEncoder;
    private final ClockHolder clockHolder;
    private final MemberRepository memberRepository;

    /**
     * 신규 회원 가입
     */
    public void signup(SignupData data) {
        memberDuplicateService.ensureSignUpNonDuplicate(data.username(), data.nickname(), data.email());

        emailVerificationService.ensureEmailVerified(data.email());

        String encodedPassword = passwordEncoder.encode(data.password());
        Member member = Member.create(data, encodedPassword, ROLE_USER, clockHolder.now());
        memberRepository.save(member);
    }
}
