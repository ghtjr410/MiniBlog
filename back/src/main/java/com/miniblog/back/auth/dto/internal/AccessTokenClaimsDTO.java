package com.miniblog.back.auth.dto.internal;

import java.util.List;

public record AccessTokenClaimsDTO(
        List<String> roles,
        String type
) {
}
