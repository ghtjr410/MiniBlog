package com.miniblog.api.member.infrastructure.mail;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum MailErrorMessage implements BaseMessage {

    UNSUPPORTED_MAIL_TYPE("지원하지 않는 메일 타입입니다."),
    UNSUPPORTED_CODE_TYPE("지원하지 않는 코드 타입입니다.");

    private final String message;

    MailErrorMessage(String message) {
        this.message = message;
    }
}
