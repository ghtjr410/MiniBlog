package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.dto.RefreshTokenClaimsData;
import com.miniblog.api.auth.application.port.TokenParser;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final TokenParser tokenParser;
    private final ClockHolder clockHolder;

    /**
     * 리프레시 토큰이 유효한지 검증
     * 서명 검증 및 만료 여부를 확인
     */
    public void validate(RefreshToken refreshToken, String receivedToken, String deviceInfo) {
        RefreshTokenClaimsData claims = tokenParser.extractRefreshTokenClaims(receivedToken);

        refreshToken.validate(
                receivedToken,
                deviceInfo,
                claims,
                clockHolder.now());
    }
}
