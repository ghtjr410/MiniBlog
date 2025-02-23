package com.miniblog.api.member.application.business;

import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.application.support.EmailCodeIssuer;
import com.miniblog.api.member.application.support.EmailCodeVerifier;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.EmailCodeType.USERNAME_RECOVERY_CODE;
import static com.miniblog.api.member.infrastructure.mail.MailSenderType.USERNAME_RECOVERY_MAIL;

@Service
@RequiredArgsConstructor
public class UsernameRecoverService {

    private final MemberReader memberReader;
    private final EmailCodeIssuer emailCodeIssuer;
    private final EmailCodeVerifier emailCodeVerifier;
    private final EmailCodeRepository emailCodeRepository;

    /**
     * 아이디 복구를 위한 인증 코드 발급
     */
    public void issueCode(String email) {
        Member member = memberReader.getByEmail(email);
        emailCodeIssuer.issueCode(member.getEmail(), USERNAME_RECOVERY_CODE, USERNAME_RECOVERY_MAIL);
    }

    /**
     * 인증 코드 검증 후 아이디 반환
     */
    @Transactional
    public String verifyCode(String email, String code) {
        Member member = memberReader.getByEmail(email);
        emailCodeVerifier.verifyCode(email, code, USERNAME_RECOVERY_CODE);

        emailCodeRepository.deleteByEmailAndType(email, USERNAME_RECOVERY_CODE);

        return member.getUsername();
    }
}
