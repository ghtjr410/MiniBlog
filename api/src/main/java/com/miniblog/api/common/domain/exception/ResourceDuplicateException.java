package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;

public class ResourceDuplicateException extends RuntimeException{

    public ResourceDuplicateException() {
    }

    public ResourceDuplicateException(BaseMessage message) {
        super(message.getMessage());
    }

    public ResourceDuplicateException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ResourceDuplicateException(Throwable cause) {
        super(cause);
    }
}
