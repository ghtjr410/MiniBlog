package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;

public class ResourceInvalidException extends RuntimeException{

    public ResourceInvalidException() {
        super("잘못된 요청입니다.");
    }

    public ResourceInvalidException(BaseMessage message) {
        super(message.getMessage());
    }

    public ResourceInvalidException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ResourceInvalidException(Throwable cause) {
        super(cause);
    }
}
