package com.miniblog.api.post.application.port;

import java.util.Map;
import java.util.Set;

public interface PostViewsCacheStore {

    /**
     * 특정 게시글의 조회수를 증가
     */
    void incrementViewCount(Long postId);

    /**
     * 모든 게시글의 캐시된 조회수를 조회
     */
    Map<Long, Integer> getAllViewCounts();

    /**
     * 특정 키에 해당하는 조회수 캐시 데이터를 삭제
     */
    void deleteViewCounts(Set<String> keys);
}
