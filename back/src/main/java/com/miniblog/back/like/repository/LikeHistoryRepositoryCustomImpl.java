package com.miniblog.back.like.repository;


import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.miniblog.back.like.model.QLikeHistory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LikeHistoryRepositoryCustomImpl implements LikeHistoryRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QLikeHistory likeHistory = QLikeHistory.likeHistory;

    @Override
    public boolean isOwnedByUser(Long likeHistoryId, Long memberId) {
        return queryFactory
                .selectOne()
                .from(likeHistory)
                .where(likeHistory.id.eq(likeHistoryId)
                        .and(likeHistory.member.id.eq(memberId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteByCondition(DeleteConditionDTO conditionDTO) {
        BooleanExpression condition = getCondition(conditionDTO.column(), conditionDTO.value());

        if (condition != null) {
            queryFactory
                    .delete(likeHistory)
                    .where(condition)
                    .execute();
        } else {
            throw new IllegalArgumentException("유효하지 않은 컬럼 또는 값입니다.");
        }
    }

    @Override
    public void deleteByPostIdsOrMemberId(List<Long> postIds, Long memberId) {

        queryFactory.delete(likeHistory)
                .where(likeHistory.post.id.in(postIds)
                        .or(likeHistory.member.id.eq(memberId)))
                .execute();
    }

    private BooleanExpression getCondition(String column, Long value) {
        return switch (column) {
            case "id" -> likeHistory.id.eq(value);
            case "post_id" -> likeHistory.post.id.eq(value);
            case "member_id" -> likeHistory.member.id.eq(value);
            default -> null;
        };
    }
}
