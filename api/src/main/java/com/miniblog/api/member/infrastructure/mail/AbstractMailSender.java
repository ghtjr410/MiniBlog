package com.miniblog.api.member.infrastructure.mail;

import com.miniblog.api.member.application.port.MailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RequiredArgsConstructor
public abstract class AbstractMailSender implements MailSender {

    private final JavaMailSender javaMailSender;

    /**
     * 메일 타입 반환
     */
    protected abstract MailSenderType getMailSenderType();

    /**
     * 메일 타입 조회
     */
    @Override
    public MailSenderType getType() {
        return getMailSenderType();
    }

    /**
     * 이메일 전송
     */
    @Override
    public void send(String email, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(getMailSenderType().getSubject());
        message.setText(text);
        javaMailSender.send(message);
    }
}
