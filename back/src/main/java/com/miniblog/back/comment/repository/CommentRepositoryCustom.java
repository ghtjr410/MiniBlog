package com.miniblog.back.comment.repository;

import com.miniblog.back.comment.dto.internal.PostIdAndMemberIdDTO;
import com.miniblog.back.common.dto.internal.DeleteConditionDTO;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {

    // Command
    void deleteByCondition(DeleteConditionDTO conditionDTO);
    void deleteByPostIdsOrMemberId(List<Long> postIds, Long memberId);

    // Query
    boolean isOwnedByUser(Long commentId, Long memberId);
    Optional<PostIdAndMemberIdDTO> findPostIdAndMemberIdById(Long commentId);
}
