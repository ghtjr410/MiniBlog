package com.miniblog.api.member.application.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberActivityEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * 회원 활동 이벤트 발행
     */
    public void publishActivity(long memberId) {
        eventPublisher.publishEvent(new MemberActivityEvent(memberId));
    }
}
