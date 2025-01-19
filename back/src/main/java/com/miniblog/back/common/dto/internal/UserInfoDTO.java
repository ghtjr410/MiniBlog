package com.miniblog.back.common.dto.internal;

import com.miniblog.back.member.model.Member;

public record UserInfoDTO(
        Long id,
        String username,
        String nickname,
        String email
) {
    public static UserInfoDTO of(Member member) {
        return new UserInfoDTO(
                member.getId(),
                member.getUsername(),
                member.getNickname(),
                member.getEmail()
        );
    }

    public static UserInfoDTO of(Long id, String username, String nickname, String email) {
        return new UserInfoDTO(
                id,
                username,
                nickname,
                email
        );
    }
}
