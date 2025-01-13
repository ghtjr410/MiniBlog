package com.miniblog.back.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BlacklistTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    public void addToBlacklist(String refreshToken, LocalDateTime expiresDate) {
        long expirationInSeconds = expiresDate.atZone(ZoneId.systemDefault()).toEpochSecond();
        long ttl = expirationInSeconds - System.currentTimeMillis() / 1000;

        if (ttl > 0) {
            redisTemplate.opsForValue().set(refreshToken, "blacklisted", ttl, TimeUnit.SECONDS);
        } else {
            throw new IllegalArgumentException("Expiration date must be in the future.");
        }
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    public boolean isBlacklistedWithLua(String token) {
        String script = "return redis.call('EXISTS', KEYS[1])";
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);
        return Boolean.TRUE.equals(redisTemplate.execute(redisScript, Collections.singletonList(token)));
    }

}
