package com.miniblog.back.auth.dto.internal;

public record AccessTokenClaimsDTO(
        Object roles,
        String type
) {
}
