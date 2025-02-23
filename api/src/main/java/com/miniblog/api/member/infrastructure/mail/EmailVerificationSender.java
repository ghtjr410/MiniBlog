package com.miniblog.api.member.infrastructure.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.miniblog.api.member.infrastructure.mail.MailSenderType.EMAIL_VERIFICATION_MAIL;

@Component
public class EmailVerificationSender extends AbstractMailSender {

    public EmailVerificationSender(JavaMailSender javaMailSender) {
        super(javaMailSender);
    }

    /**
     * 이메일 인증 메일 타입 반환
     */
    @Override
    protected MailSenderType getMailSenderType() {
        return EMAIL_VERIFICATION_MAIL;
    }
}
