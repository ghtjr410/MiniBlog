package com.miniblog.back.post.repository;

import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.miniblog.back.post.model.QPost;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PostRepositoryCustomImpl implements PostRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;

    @Override
    public boolean isPostOwnedByUser(Long postId, Long memberId) {
        return queryFactory
                .selectOne()
                .from(post)
                .where(post.id.eq(postId)
                        .and(post.member.id.eq(memberId)))
                .fetchFirst() != null;
    }

    @Override
    public void deleteByCondition(DeleteConditionDTO conditionDTO) {
        BooleanExpression condition = getCondition(conditionDTO.column(), conditionDTO.value());

        if (condition != null) {
            queryFactory
                    .delete(post)
                    .where(condition)
                    .execute();
        } else {
            throw new IllegalArgumentException("유효하지 않은 컬럼 또는 값입니다.");
        }
    }

    private BooleanExpression getCondition(String column, Long value) {
        return switch (column) {
            case "id" -> post.id.eq(value);
            case "member_id" -> post.member.id.eq(value);
            default -> null;
        };
    }

    @Override
    public List<Long> findPostIdsByMemberId(Long memberId) {
        return queryFactory
                .select(post.id)
                .from(post)
                .where(post.member.id.eq(memberId))
                .fetch();
    }

    @Override
    public void deletePostsByIds(List<Long> postIds) {
        queryFactory.delete(post)
                .where(post.id.in(postIds))
                .execute();
    }

    @Override
    public Optional<Long> findMemberIdById(Long postId) {
        return Optional.ofNullable(
                queryFactory
                        .select(post.member.id)
                        .from(post)
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }
}
