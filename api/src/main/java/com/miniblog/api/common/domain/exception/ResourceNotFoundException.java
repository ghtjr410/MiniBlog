package com.miniblog.api.common.domain.exception;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(BaseMessage message) {
        super(message.getMessage());
    }

    public ResourceNotFoundException(BaseMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }
}
