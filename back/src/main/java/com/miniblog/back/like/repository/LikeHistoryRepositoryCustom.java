package com.miniblog.back.like.repository;

import com.miniblog.back.common.dto.internal.DeleteConditionDTO;

import java.util.List;

public interface LikeHistoryRepositoryCustom {
    // Command
    void deleteByCondition(DeleteConditionDTO conditionDTO);
    void deleteByPostIdsOrMemberId(List<Long> postIds, Long memberId);

    // Query
    boolean isOwnedByUser(Long likeHistoryId, Long memberId);
}
