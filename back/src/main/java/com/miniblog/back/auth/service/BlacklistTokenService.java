package com.miniblog.back.auth.service;

import com.miniblog.back.auth.token.TokenValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlacklistTokenService {
    private final TokenValidator tokenValidator;
    private final RedisTemplate<String, String> redisTemplate;

    public void addToBlacklist(String refreshToken) {
        LocalDateTime expiresDate = tokenValidator.getExpirationDate(refreshToken);
        long expirationInSeconds = expiresDate.atZone(ZoneId.systemDefault()).toEpochSecond();
        long ttl = expirationInSeconds - System.currentTimeMillis() / 1000;

        if (ttl > 0) {
            redisTemplate.opsForValue().set(refreshToken, "blacklisted", ttl, TimeUnit.SECONDS);
        }
    }

    public boolean isBlacklistedWithLua(String token) {
        log.info("5. 블랙리스트 검사 도착");
        String script = "return redis.call('EXISTS', KEYS[1])";
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);
        return Boolean.TRUE.equals(redisTemplate.execute(redisScript, Collections.singletonList(token)));
    }

}
