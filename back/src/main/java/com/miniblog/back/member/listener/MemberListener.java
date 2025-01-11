package com.miniblog.back.member.listener;

import com.miniblog.back.auth.util.RoleType;
import com.miniblog.back.member.model.Member;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class MemberListener {

    @PrePersist
    public void prePersist(Member member) {
        if (member.getRole() == null) {
            member.setRole(RoleType.USER);
        }
        if (member.getCreatedDate() == null) {
            member.setCreatedDate(LocalDateTime.now());
        }
    }
}
