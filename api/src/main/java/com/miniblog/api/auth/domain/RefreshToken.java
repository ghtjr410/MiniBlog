package com.miniblog.api.auth.domain;

import com.miniblog.api.auth.application.dto.RefreshTokenClaimsData;
import com.miniblog.api.auth.application.dto.RefreshTokenInfoData;
import com.miniblog.api.common.domain.exception.ResourceExpiredException;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_INVALID_CREDENTIALS;
import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_TOKEN_EXPIRED;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(
        name = "refresh_token",
        indexes = {
                @Index(name = "idx_token", columnList = "token"),
                @Index(name = "idx_member_id", columnList = "member_id"),
                @Index(name = "idx_expires_date", columnList = "expires_date")
        }
)
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id;

    @Column(name = "token", unique = true, nullable = false, length = 512)
    private String token;

    @Column(name = "device_info", nullable = false)
    private String deviceInfo;

    @Column(name = "expires_date", nullable = false)
    private LocalDateTime expiresDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static RefreshToken create(RefreshTokenInfoData data, String deviceInfo, Member member) {
        return RefreshToken.builder()
                .token(data.refreshToken())
                .deviceInfo(deviceInfo)
                .expiresDate(data.expiresDate())
                .member(member)
                .build();
    }

    public void validate(String token, String deviceInfo, RefreshTokenClaimsData claimsData, LocalDateTime now) {
        checkTokenMatches(token);
        checkMemberIdMatches(claimsData.memberId());
        checkDeviceInfoMatches(deviceInfo);
        checkExpirationMatches(claimsData.expiresDate());
        checkExpired(now);
    }

    private void checkExpired(LocalDateTime now) {
        if (this.expiresDate.isBefore(now)) {
            throw new ResourceExpiredException(AUTH_TOKEN_EXPIRED);
        }
    }

    private void checkExpirationMatches(LocalDateTime expiresDate) {
        if (!this.expiresDate.equals(expiresDate)) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }

    private void checkDeviceInfoMatches(String deviceInfo) {
        if (!StringUtils.equals(this.deviceInfo, deviceInfo)) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }

    private void checkMemberIdMatches(long memberId) {
        if (this.member.getId() != memberId) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }

    private void checkTokenMatches(String token) {
        if (!StringUtils.equals(this.token, token)) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }
}
