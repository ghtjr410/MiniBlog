package com.miniblog.api.member.infrastructure.mail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import static com.miniblog.api.member.infrastructure.mail.MailSenderType.PASSWORD_CHANGE_MAIL;

@Component
public class PasswordNotificationSender extends AbstractMailSender {

    public PasswordNotificationSender(JavaMailSender javaMailSender) {
        super(javaMailSender);
    }

    /**
     * 비밀번호 변경 메일 타입 반환
     */
    @Override
    protected MailSenderType getMailSenderType() {
        return PASSWORD_CHANGE_MAIL;
    }
}
