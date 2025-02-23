package com.miniblog.api.member.application.dto;

public record SignupData(
        String username,
        String password,
        String email,
        String nickname
) {
    public static SignupData of(String username, String password, String email, String nickname) {
        return new SignupData(username, password, email, nickname);
    }
}
