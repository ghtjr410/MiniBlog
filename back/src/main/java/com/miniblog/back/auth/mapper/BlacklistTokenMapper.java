package com.miniblog.back.auth.mapper;

import com.miniblog.back.auth.model.BlacklistToken;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlacklistTokenMapper {

    public BlacklistToken create (String refreshToken, LocalDateTime expiresDate) {
        return BlacklistToken.builder()
                .refreshToken(refreshToken)
                .expiresDate(expiresDate)
                .build();
    }
}
