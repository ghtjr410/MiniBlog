package com.miniblog.api.member.api.dto.response;

public record NicknameChangeResponse(
        String nickname
) {
    public static NicknameChangeResponse of(String nickname) {
        return new NicknameChangeResponse(nickname);
    }
}
