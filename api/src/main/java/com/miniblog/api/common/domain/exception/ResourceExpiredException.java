package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;

public class ResourceExpiredException extends RuntimeException{

    public ResourceExpiredException() {
    }

    public ResourceExpiredException(BaseMessage message) {
        super(message.getMessage());
    }

    public ResourceExpiredException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ResourceExpiredException(Throwable cause) {
        super(cause);
    }
}
