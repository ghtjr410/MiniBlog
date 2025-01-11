package com.miniblog.back.auth.service;


import com.miniblog.back.auth.dto.internal.AccessTokenClaimsDTO;
import com.miniblog.back.auth.mapper.BlacklistTokenMapper;
import com.miniblog.back.auth.model.BlacklistToken;
import com.miniblog.back.auth.model.Token;
import com.miniblog.back.auth.mapper.TokenMapper;
import com.miniblog.back.auth.repository.BlacklistTokenRepository;
import com.miniblog.back.auth.util.TokenProvider;
import com.miniblog.back.auth.repository.TokenRepository;
import com.miniblog.back.auth.dto.internal.TokensDTO;
import com.miniblog.back.auth.dto.internal.RefreshTokenInfoDTO;
import com.miniblog.back.auth.util.TokenType;
import com.miniblog.back.auth.util.TokenValidator;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;
    private final TokenMapper tokenMapper;
    private final BlacklistTokenMapper blacklistTokenMapper;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final BlacklistTokenRepository blacklistTokenRepository;

    public TokensDTO generateTokens(String username, Object claim, String deviceInfo) {

        AccessTokenClaimsDTO tokenClaims = new AccessTokenClaimsDTO(claim, TokenType.ACCESS_TOKEN.getValue());


        // 토큰 생성
        String accessToken = tokenProvider.createAccessToken(username, tokenClaims);
        RefreshTokenInfoDTO refreshTokenResult = tokenProvider.createRefreshToken(username);

        // 데이터베이스에 저장
        saveToken(username, refreshTokenResult, deviceInfo);

        return new TokensDTO(accessToken, refreshTokenResult.refreshToken());
    }

    @Transactional
    private void saveToken(String username, RefreshTokenInfoDTO refreshTokenResult, String deviceInfo) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));

        Token token = tokenMapper.create(member, refreshTokenResult.refreshToken(), refreshTokenResult.expiresDate(), deviceInfo);
        tokenRepository.save(token);
    }


    @Transactional
    public void revokeRefreshToken(String refreshToken) {
        // 1. Refresh Token 유효성 검증
        if (!tokenValidator.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired Refresh Token.");
        }

        // 2. Refresh Token 데이터베이스에서 삭제
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Token not found in database."));
        tokenRepository.delete(token);

        // 3. 블랙리스트에 추가
        LocalDateTime expiresDate = tokenValidator.getExpirationDate(refreshToken);
        BlacklistToken blacklistToken = blacklistTokenMapper.create(refreshToken, expiresDate);

        blacklistTokenRepository.save(blacklistToken);
        log.info("Refresh Token {} has been revoked and added to blacklist.", refreshToken);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistTokenRepository.existsByRefreshToken(token); // 블랙리스트에 있으면 true 반환
    }
}
