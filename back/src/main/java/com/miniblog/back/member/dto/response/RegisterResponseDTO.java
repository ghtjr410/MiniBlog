package com.miniblog.back.member.dto.response;

public record RegisterResponseDTO(
        String nickname
) {
    public static RegisterResponseDTO of(String nickname) {
        return new RegisterResponseDTO(
                nickname
        );
    }
}
