package com.miniblog.api.member.application.business;

import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.application.support.EmailCodeIssuer;
import com.miniblog.api.member.application.support.EmailCodeVerifier;
import com.miniblog.api.member.application.support.EmailCodeReader;
import com.miniblog.api.member.domain.EmailCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.EmailCodeType.EMAIL_VERIFICATION_CODE;
import static com.miniblog.api.member.infrastructure.mail.MailSenderType.*;

@Component
@RequiredArgsConstructor
public class EmailVerificationService {

    private final EmailCodeIssuer emailCodeIssuer;
    private final EmailCodeVerifier emailCodeVerifier;
    private final EmailCodeReader emailCodeReader;
    private final EmailCodeRepository emailCodeRepository;

    /**
     * 이메일 인증 코드 발급
     */
    public void issueCode(String email) {
        emailCodeIssuer.issueCode(email, EMAIL_VERIFICATION_CODE, EMAIL_VERIFICATION_MAIL);
    }

    /**
     * 이메일 인증 코드 검증
     */
    @Transactional
    public void verifyCode(String email, String code) {
        emailCodeVerifier.verifyCode(email, code, EMAIL_VERIFICATION_CODE);
    }

    /**
     * 이메일이 인증되었는지 확인 후 인증 정보 삭제
     */
    @Transactional
    public void ensureEmailVerified(String email) {
        EmailCode emailCode = emailCodeReader.getByEmailAndType(email, EMAIL_VERIFICATION_CODE);

        emailCode.ensureVerified();
        emailCodeRepository.delete(emailCode);
    }
}
