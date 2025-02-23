package com.miniblog.api.post.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PostViewsRedisStore {

    private final RedisTemplate<String, String> redisTemplate;

    public void incrementViewCount(Long postId) {
        redisTemplate.opsForValue().increment("post:" + postId);
    }

    public Map<Long, Integer> getAllViewCounts() {
        Set<String> keys = redisTemplate.keys("post:*");

        if (keys.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, Integer> viewCounts = new HashMap<>();
        for (String key : keys) {
            Long postId = Long.parseLong(key.replace("post:", ""));
            String count = redisTemplate.opsForValue().get(key);

            if (count != null) {
                viewCounts.put(postId, Integer.parseInt(count));
            }
        }
        return viewCounts;
    }

    public void deleteViewCounts(Set<String> keys) {
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
