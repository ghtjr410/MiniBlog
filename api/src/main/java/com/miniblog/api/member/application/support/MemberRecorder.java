package com.miniblog.api.member.application.support;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.port.MemberRepository;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRecorder {

    private final ClockHolder clockHolder;
    private final MemberRepository memberRepository;

    /**
     * 사용자의 마지막 로그인 시간 기록
     */
    @Transactional
    public void recordLastLogin(Member member) {
        member.recordLastLoginDate(clockHolder.now());
    }

    /**
     * 사용자의 마지막 활동 시간 기록 (독립 트랜잭션으로 실행)
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordLastActivity(Long memberId) {
        memberRepository.updateLastActivity(memberId, clockHolder.now());
    }
}
