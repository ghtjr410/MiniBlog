package com.miniblog.api.auth.application.port;

import com.miniblog.api.auth.domain.RefreshToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    void deleteByToken(String token);
    void delete(RefreshToken refreshToken);
    void deleteExpiredTokens(LocalDateTime now);
    void deleteAllByIdInBatch(List<Long> ids);
    void deleteAllByMemberId(Long memberId);

    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByMemberId(Long memberId);
}
