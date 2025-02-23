package com.miniblog.api.member.infrastructure.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.miniblog.api.member.infrastructure.mail.MailSenderType.USERNAME_RECOVERY_MAIL;

@Component
public class UsernameRecoverSender extends AbstractMailSender {

    public UsernameRecoverSender(JavaMailSender javaMailSender) {
        super(javaMailSender);
    }

    /**
     * 아이디 찾기 메일 타입 반환
     */
    @Override
    protected MailSenderType getMailSenderType() {
        return USERNAME_RECOVERY_MAIL;
    }
}
