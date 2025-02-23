package com.miniblog.api.member.application.event;

import com.miniblog.api.member.application.support.MemberRecorder;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class MemberActivityEventListener {

    private final MemberRecorder memberRecorder;

    /**
     * 회원 활동 이벤트 처리
     * - 회원 활동이 감지되면 비동기적으로 마지막 활동 시간을 기록
     * - 트랜잭션 커밋 후 실행되며, 새로운 트랜잭션에서 실행됨
     */
    @Async("memberActivityTaskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleMemberActivity(MemberActivityEvent event) {
        memberRecorder.recordLastActivity(event.getMemberId());
    }
}
