package com.miniblog.back.listener;

import com.miniblog.back.model.Member;
import com.miniblog.back.util.RoleType;
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
