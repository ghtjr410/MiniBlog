package com.miniblog.api.auth.domain;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum AuthErrorMessage implements BaseMessage {
    AUTH_TOKEN_EXPIRED("인증 토큰이 만료되었습니다."),
    AUTH_INVALID_CREDENTIALS("잘못된 인증 정보입니다."),
    AUTH_MISSING_CREDENTIALS("인증 정보가 존재하지 않습니다.");

    private final String message;

    AuthErrorMessage(String message) {
        this.message = message;
    }
}
