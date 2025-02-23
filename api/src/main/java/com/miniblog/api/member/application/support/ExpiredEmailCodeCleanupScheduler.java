package com.miniblog.api.member.application.support;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.port.EmailCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpiredEmailCodeCleanupScheduler {

    private final EmailCodeRepository emailCodeRepository;
    private final ClockHolder clockHolder;

    /**
     * 10분마다 만료된 이메일 코드 정리
     */
    @Scheduled(fixedRate = 600000)
    public void cleanupExpiredEmailCodes() {
        emailCodeRepository.deleteExpiredCodes(clockHolder.now());
    }
}
