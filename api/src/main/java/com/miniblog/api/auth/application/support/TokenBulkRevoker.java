package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.port.BlacklistTokenCacheStore;
import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.domain.BlacklistToken;
import com.miniblog.api.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenBulkRevoker {

    private final TokenReader tokenReader;
    private final RefreshTokenRepository refreshTokenRepository;
    private final BlacklistTokenCacheStore blacklistTokenCacheStore;

    /**
     * 여러 개의 리프레시 토큰을 한 번에 폐기
     * 블랙리스트에 추가 및 데이터베이스에서 삭제
     */
    public void revokeAll(long memberId) {
        List<RefreshToken> refreshTokens = tokenReader.getAllByMemberId(memberId);
        if (refreshTokens.isEmpty()) return;

        List<Long> tokenIds = refreshTokens.stream()
                .map(RefreshToken::getId)
                .toList();

        refreshTokenRepository.deleteAllByIdInBatch(tokenIds);

        List<BlacklistToken> blacklistTokens = refreshTokens.stream()
                .map(tokenData -> BlacklistToken.create(tokenData.getToken(), tokenData.getExpiresDate()))
                .toList();

        blacklistTokenCacheStore.saveAll(blacklistTokens);
    }

}
