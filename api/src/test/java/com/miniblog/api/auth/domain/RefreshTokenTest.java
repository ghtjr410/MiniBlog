package com.miniblog.api.auth.domain;

import com.miniblog.api.auth.application.dto.RefreshTokenClaimsData;
import com.miniblog.api.auth.application.dto.RefreshTokenInfoData;
import com.miniblog.api.common.domain.exception.ResourceExpiredException;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RefreshTokenTest {

    private final String token = "refreshToken";
    private final String deviceInfo = "deviceInfo";
    private final LocalDateTime expiresDate = LocalDateTime.now().plusDays(7);
    private final Member member = Member.builder().id(1L).build();

    @Test
    void 리프레시_토큰을_정상적으로_발급한다() {
        // Given
        RefreshTokenInfoData tokenData = RefreshTokenInfoData.of(token, expiresDate);

        // When
        RefreshToken refreshToken = RefreshToken.create(tokenData, deviceInfo, member);

        // Then
        assertThat(refreshToken.getId()).isNull();
        assertThat(refreshToken.getToken()).isEqualTo(token);
        assertThat(refreshToken.getDeviceInfo()).isEqualTo(deviceInfo);
        assertThat(refreshToken.getExpiresDate()).isEqualTo(expiresDate);
        assertThat(refreshToken.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void 유효한_리프레시_토큰_검증에_성공한다() {
        // Given
        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expiresDate);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(member.getId(), expiresDate);

        // When & Then
        assertThatCode(() -> refreshToken.validate(token, deviceInfo, claimsData, LocalDateTime.now()))
                .doesNotThrowAnyException();
    }

    @Test
    void 잘못된_토큰을_입력하면_검증에_실패한다() {
        // Given
        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expiresDate);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(member.getId(), expiresDate);

        // When & Then
        assertThatThrownBy(() -> refreshToken.validate("wrong-token", deviceInfo, claimsData, LocalDateTime.now()))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }

    @Test
    void 잘못된_회원_ID를_입력하면_검증에_실패한다() {
        // Given
        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expiresDate);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(1000L, expiresDate);

        // When & Then
        assertThatThrownBy(() -> refreshToken.validate(token, deviceInfo, claimsData, LocalDateTime.now()))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }

    @Test
    void 잘못된_디바이스_정보를_입력하면_검증에_실패한다() {
        // Given
        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expiresDate);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(member.getId(), expiresDate);

        // When & Then
        assertThatThrownBy(() -> refreshToken.validate(token, "wrongDeviceInfo", claimsData, LocalDateTime.now()))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }

    @Test
    void 잘못된_만료_날짜를_입력하면_검증에_실패한다() {
        // Given
        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expiresDate);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(member.getId(), LocalDateTime.now().plusDays(20));

        // When & Then
        assertThatThrownBy(() -> refreshToken.validate(token, deviceInfo, claimsData, LocalDateTime.now()))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }

    @Test
    void 만료된_리프레시_토큰은_검증에_실패한다() {
        // Given
        LocalDateTime expired = LocalDateTime.now().minusMinutes(1);

        RefreshTokenInfoData tokenInfo = RefreshTokenInfoData.of(token, expired);
        RefreshToken refreshToken = RefreshToken.create(tokenInfo, deviceInfo, member);
        RefreshTokenClaimsData claimsData = RefreshTokenClaimsData.of(member.getId(), expired);

        // When & Then
        assertThatThrownBy(() -> refreshToken.validate(token, deviceInfo, claimsData, LocalDateTime.now()))
                .isInstanceOf(ResourceExpiredException.class);
    }

}