package com.miniblog.api.auth.infrastructure.redis;

import com.miniblog.api.auth.domain.BlacklistToken;
import com.miniblog.api.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BlacklistTokenRedisStore {

    private final RedisTemplate<String, String> redisTemplate;
    private final ClockHolder clockHolder;

    public void save(BlacklistToken blacklistToken) {
        long ttl = blacklistToken.getTtlInSeconds(clockHolder.now());
        if (ttl > 0) {
            redisTemplate.opsForValue().set(
                    blacklistToken.getToken(),
                    "blacklisted",
                    ttl,
                    TimeUnit.SECONDS
            );
        }
    }

    public void saveAll(List<BlacklistToken> blacklistTokens) {
        LocalDateTime now = clockHolder.now();

        redisTemplate.executePipelined((RedisCallback<Void>) connection -> {
            StringRedisConnection stringRedisConnection = (StringRedisConnection) connection; // StringRedisConnection 활용

            for (BlacklistToken token : blacklistTokens) {
                long ttl = token.getTtlInSeconds(now);
                if (ttl > 0) {
                    stringRedisConnection.set(token.getToken(), "blacklisted");
                    stringRedisConnection.expire(token.getToken(), ttl);
                }
            }
            return null;
        });
    }

    public boolean existsByToken(String token) {
        String script = "return redis.call('EXISTS', KEYS[1])";
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>(script, Boolean.class);
        return Boolean.TRUE.equals(redisTemplate.execute(redisScript, Collections.singletonList(token)));
    }




}
