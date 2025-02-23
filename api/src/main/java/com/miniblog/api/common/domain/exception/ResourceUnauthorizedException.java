package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;

public class ResourceUnauthorizedException extends RuntimeException{

    public ResourceUnauthorizedException() {
    }

    public ResourceUnauthorizedException(BaseMessage message) {
        super(message.getMessage());
    }

    public ResourceUnauthorizedException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ResourceUnauthorizedException(Throwable cause) {
        super(cause);
    }
}
