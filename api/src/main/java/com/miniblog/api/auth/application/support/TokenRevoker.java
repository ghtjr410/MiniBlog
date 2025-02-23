package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.port.BlacklistTokenCacheStore;
import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.domain.BlacklistToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TokenRevoker {

    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenCacheStore blacklistTokenCacheStore;

    /**
     * 특정 리프레시 토큰을 폐기 및 블랙리스트에 추가
     */
    public void revoke(String refreshToken, LocalDateTime expiresDate) {
        BlacklistToken blacklistToken = BlacklistToken.create(refreshToken, expiresDate);
        blacklistTokenCacheStore.save(blacklistToken);
        refreshTokenRepository.deleteByToken(refreshToken);
    }
}
