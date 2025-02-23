package com.miniblog.api.member.application.event;

import lombok.Getter;

@Getter
public class MemberActivityEvent {

    private final long memberId;

    /**
     * 회원 활동 이벤트 생성
     */
    public MemberActivityEvent(long memberId) {
        this.memberId = memberId;
    }
}
