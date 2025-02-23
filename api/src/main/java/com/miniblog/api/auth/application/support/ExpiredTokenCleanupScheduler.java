package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ExpiredTokenCleanupScheduler {

    private final RefreshTokenRepository refreshTokenRepository;
    private final ClockHolder clockHolder;

    /**
     * 만료된 리프레시 토큰을 정기적으로 삭제
     * 매일 00:00에 실행
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanupExpiredTokens() {
        refreshTokenRepository.deleteExpiredTokens(clockHolder.now());
    }
}
