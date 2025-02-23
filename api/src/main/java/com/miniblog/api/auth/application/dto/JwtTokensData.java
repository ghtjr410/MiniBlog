package com.miniblog.api.auth.application.dto;

public record JwtTokensData(
        AccessTokenInfoData accessTokenInfo,
        RefreshTokenInfoData refreshTokenInfo
) {
    public static JwtTokensData of(AccessTokenInfoData access, RefreshTokenInfoData refresh) {
        return new JwtTokensData(access, refresh);
    }
}
