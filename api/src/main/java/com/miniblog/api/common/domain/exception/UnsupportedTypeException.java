package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;
import com.miniblog.api.common.domain.BaseType;

public class UnsupportedTypeException extends RuntimeException{

    public UnsupportedTypeException() {
        super("지원하지 않는 타입입니다.");
    }

    public UnsupportedTypeException(BaseMessage message, BaseType type) {
        super(message.getMessage()+ ": " + type);
    }

    public UnsupportedTypeException(BaseMessage message, BaseType type, Throwable cause) {
        super(message.getMessage()+ ": " + type, cause);
    }

    public UnsupportedTypeException(Throwable cause) {
        super(cause);
    }
}
