package com.miniblog.api.member.infrastructure.mail;

import com.miniblog.api.common.domain.exception.UnsupportedTypeException;
import com.miniblog.api.member.application.port.MailSender;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.miniblog.api.member.infrastructure.mail.MailErrorMessage.*;

@Component
public class MailSenderFactory {
    private final Map<MailSenderType, MailSender> senderMap;

    public MailSenderFactory(List<MailSender> senders) {
        this.senderMap = senders.stream()
                .collect(Collectors.toMap(MailSender::getType, Function.identity()));
    }

    /**
     * 메일 전송
     */
    public void sendMail(String email, String message, MailSenderType type) {
        MailSender sender = senderMap.get(type);
        if (sender == null) {
            throw new UnsupportedTypeException(UNSUPPORTED_MAIL_TYPE, type);
        }

        sender.send(email, message);
    }
}
