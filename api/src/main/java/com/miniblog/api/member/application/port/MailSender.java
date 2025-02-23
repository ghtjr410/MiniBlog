package com.miniblog.api.member.application.port;

import com.miniblog.api.member.infrastructure.mail.MailSenderType;

public interface MailSender {
    MailSenderType getType();
    void send(String email, String text);
}
