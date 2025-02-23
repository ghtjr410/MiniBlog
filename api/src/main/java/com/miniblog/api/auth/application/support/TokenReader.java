package com.miniblog.api.auth.application.support;

import com.miniblog.api.auth.application.port.RefreshTokenRepository;
import com.miniblog.api.auth.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenReader {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 특정 회원의 모든 리프레시 토큰을 조회
     */
    public List<RefreshToken> getAllByMemberId(long memberId) {
        return refreshTokenRepository.findAllByMemberId(memberId);
    }
}
