package com.miniblog.back.comment.repository;

import com.miniblog.back.comment.dto.internal.PostIdAndMemberIdDTO;
import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import com.miniblog.back.comment.model.QComment;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QComment comment = QComment.comment;

    @Override
    public boolean isOwnedByUser(Long commentId, Long memberId) {
        return queryFactory
                .selectOne()
                .from(comment)
                .where(comment.id.eq(commentId)
                        .and(comment.member.id.eq(memberId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteByCondition(DeleteConditionDTO conditionDTO) {
        BooleanExpression condition = getCondition(conditionDTO.column(), conditionDTO.value());

        if (condition != null) {
            queryFactory
                    .delete(comment)
                    .where(condition)
                    .execute();
        } else {
            throw new IllegalArgumentException("유효하지 않은 컬럼 또는 값입니다.");
        }
    }

    private BooleanExpression getCondition(String column, Long value) {
        return switch (column) {
            case "id" -> comment.id.eq(value);
            case "post_id" -> comment.post.id.eq(value);
            case "member_id" -> comment.member.id.eq(value);
            default -> null;
        };
    }

    @Override
    public void deleteByPostIdsOrMemberId(List<Long> postIds, Long memberId) {
        queryFactory.delete(comment)
                .where(comment.post.id.in(postIds)
                        .or(comment.member.id.eq(memberId)))
                .execute();
    }

    @Override
    public Optional<PostIdAndMemberIdDTO> findPostIdAndMemberIdById(Long commentId) {
        return Optional.ofNullable(
            queryFactory
                    .select(Projections.constructor(
                            PostIdAndMemberIdDTO.class,
                            comment.post.id,
                            comment.member.id
                    ))
                    .from(comment)
                    .where(comment.id.eq(commentId))
                    .fetchOne()
        );
    }
}
