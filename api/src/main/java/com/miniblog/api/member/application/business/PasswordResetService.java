package com.miniblog.api.member.application.business;

import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.application.support.EmailCodeIssuer;
import com.miniblog.api.member.application.support.EmailCodeVerifier;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.member.infrastructure.code.CodeGeneratorFactory;
import com.miniblog.api.member.infrastructure.mail.MailSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.EmailCodeType.PASSWORD_RESET_CODE;
import static com.miniblog.api.member.infrastructure.mail.MailSenderType.PASSWORD_CHANGE_MAIL;
import static com.miniblog.api.member.infrastructure.mail.MailSenderType.PASSWORD_RESET_MAIL;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final MemberReader memberReader;
    private final CodeGeneratorFactory codeGeneratorFactory;
    private final EmailCodeRepository emailCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailCodeVerifier emailCodeVerifier;
    private final EmailCodeIssuer emailCodeIssuer;
    private final MailSenderFactory mailSenderFactory;

    /**
     * 비밀번호 재설정 코드 발급
     */
    public void issueCode(String username) {
        Member member = memberReader.getByUsername(username);
        emailCodeIssuer.issueCode(member.getEmail(), PASSWORD_RESET_CODE, PASSWORD_RESET_MAIL);
    }

    /**
     * 인증 코드 검증 후 임시 비밀번호 발급
     */
    @Transactional
    public void verifyCode(String username, String code) {
        Member member = memberReader.getByUsername(username);
        String email = member.getEmail();

        emailCodeVerifier.verifyCode(email, code, PASSWORD_RESET_CODE);

        String temporaryPassword = codeGeneratorFactory.generateCode(PASSWORD_RESET_CODE);
        member.changePassword(passwordEncoder.encode(temporaryPassword));

        emailCodeRepository.deleteByEmailAndType(email, PASSWORD_RESET_CODE);

        mailSenderFactory.sendMail(email, temporaryPassword, PASSWORD_CHANGE_MAIL);
    }
}
