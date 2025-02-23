package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.application.port.TokenProvider;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenIssuer {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 새로운 JWT 액세스 토큰과 리프레시 토큰을 발급
     * 리프레시 토큰은 데이터베이스에 저장
     */
    @Transactional
    public JwtTokensData issueTokens(Member member, List<String> roles, String deviceInfo) {
        JwtTokensData tokens = tokenProvider.generateTokens(member, roles);

        RefreshToken refreshToken = RefreshToken.create(tokens.refreshTokenInfo(), deviceInfo, member);
        refreshTokenRepository.save(refreshToken);

        return tokens;
    }
}
