package com.miniblog.api.auth.application.dto;

import java.util.List;

public record MemberInfoData(
        long id,
        String username,
        String nickname,
        String email,
        List<String> roles
) {
    public static MemberInfoData of(long id, String username, String nickname, String email, List<String> roles) {
        return new MemberInfoData(id, username, nickname, email, roles);
    }
}
