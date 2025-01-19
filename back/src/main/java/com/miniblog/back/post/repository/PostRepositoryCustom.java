package com.miniblog.back.post.repository;

import com.miniblog.back.common.dto.internal.DeleteConditionDTO;

import java.util.List;
import java.util.Optional;

public interface PostRepositoryCustom {
    // Command
    void deleteByCondition(DeleteConditionDTO conditionDTO);
    void deletePostsByIds(List<Long> postIds);

    // Query
    boolean isPostOwnedByUser(Long postId, Long memberId);
    List<Long> findPostIdsByMemberId(Long memberId);
    Optional<Long> findMemberIdById(Long postId);
}
