package com.miniblog.api.member.infrastructure.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.miniblog.api.member.infrastructure.mail.MailSenderType.PASSWORD_RESET_MAIL;

@Component
public class PasswordVerificationSender extends AbstractMailSender {

    public PasswordVerificationSender(JavaMailSender javaMailSender) {
        super(javaMailSender);
    }

    /**
     * 비밀번호 재설정 메일 타입 반환
     */
    @Override
    protected MailSenderType getMailSenderType() {
        return PASSWORD_RESET_MAIL;
    }
}
