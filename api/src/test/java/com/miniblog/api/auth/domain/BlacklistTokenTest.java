package com.miniblog.api.auth.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BlacklistTokenTest {

    @Test
    void 블랙리스트_토큰을_정상적으로_생성한다() {
        // Given
        String token = "blacklistToken";
        LocalDateTime expiresDate = LocalDateTime.now().plusMinutes(10);

        // When
        BlacklistToken blacklistToken = BlacklistToken.create(token, expiresDate);

        // Then
        assertThat(blacklistToken.getToken()).isEqualTo(token);
        assertThat(blacklistToken.getExpiresDate()).isEqualTo(expiresDate);
    }

    @Test
    void TTL이_올바르게_계산된다() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiresDate = now.plusMinutes(10);
        BlacklistToken blacklistToken = BlacklistToken.create("blacklistToken", expiresDate);

        // When
        long ttl = blacklistToken.getTtlInSeconds(now);

        // Then
        assertThat(ttl).isEqualTo(600);
    }

    @Test
    void TTL이_0_이하이면_블랙리스트_토큰이_만료된_상태다() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expired = now.minusMinutes(5);
        BlacklistToken blacklistToken = BlacklistToken.create("expiredBlacklistToken", expired);

        // When
        long ttl = blacklistToken.getTtlInSeconds(now);

        // Then
        assertThat(ttl).isLessThanOrEqualTo(0);
    }
}