package com.miniblog.api.member.api.dto.response;

public record UsernameResponse (
        String username
){
    public static UsernameResponse of (String username) {
        return new UsernameResponse(username);
    }
}
