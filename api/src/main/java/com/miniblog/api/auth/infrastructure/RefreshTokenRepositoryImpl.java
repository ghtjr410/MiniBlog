package com.miniblog.api.auth.infrastructure;

import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.auth.infrastructure.persistence.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {

        return refreshTokenJpaRepository.save(refreshToken);
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenJpaRepository.deleteByToken(token);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenJpaRepository.delete(refreshToken);
    }

    @Override
    public void deleteExpiredTokens(LocalDateTime now) {
        refreshTokenJpaRepository.deleteExpiredTokens(now);
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        refreshTokenJpaRepository.deleteAllByMemberId(memberId);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token);
    }

    @Override
    public List<RefreshToken> findAllByMemberId(Long memberId) {
        return refreshTokenJpaRepository.findAllByMemberId(memberId);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> ids) {
        refreshTokenJpaRepository.deleteAllByIdInBatch(ids);
    }
}
