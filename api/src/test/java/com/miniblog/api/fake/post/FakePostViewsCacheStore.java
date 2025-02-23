package com.miniblog.api.fake.post;

import com.miniblog.api.post.application.port.PostViewsCacheStore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FakePostViewsCacheStore implements PostViewsCacheStore {
    private final Map<Long, Integer> viewCountsStore = new ConcurrentHashMap<>();

    @Override
    public void incrementViewCount(Long postId) {
        viewCountsStore.put(postId, viewCountsStore.getOrDefault(postId, 0) + 1);
    }

    @Override
    public Map<Long, Integer> getAllViewCounts() {
        return new HashMap<>(viewCountsStore);
    }

    @Override
    public void deleteViewCounts(Set<String> keys) {
        for (String key : keys) {
            if (key.startsWith("post:")) {
                try {
                    Long postId = Long.parseLong(key.replace("post:", ""));
                    viewCountsStore.remove(postId);
                } catch (NumberFormatException ignored) {
                    // 잘못된 형식의 키는 무시
                }
            }
        }
    }
}
