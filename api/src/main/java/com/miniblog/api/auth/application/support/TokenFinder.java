package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.port.BlacklistTokenCacheStore;
import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_INVALID_CREDENTIALS;
import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_MISSING_CREDENTIALS;

@Component
@RequiredArgsConstructor
public class TokenFinder {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenCacheStore blacklistTokenCacheStore;

    /**
     * 주어진 토큰이 유효한지 확인 후 조회
     * 블랙리스트에 포함된 경우 예외 발생
     */
    public RefreshToken findByToken(String token) {
        ensureNotBlacklisted(token);

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceUnauthorizedException(AUTH_MISSING_CREDENTIALS));
    }

    /**
     * 토큰이 블랙리스트에 포함되지 않았는지 검증
     */
    public void ensureNotBlacklisted(String token) {
        if (blacklistTokenCacheStore.existsByToken(token)) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }
}
