package com.miniblog.back.query.repository;

import com.miniblog.back.comment.model.QComment;
import com.miniblog.back.like.model.QLikeHistory;
import com.miniblog.back.post.model.QPost;
import com.miniblog.back.post.model.QPostAggregate;
import com.miniblog.back.query.dto.internal.MemberInfoDTO;
import com.miniblog.back.query.dto.internal.PostDetailDTO;
import com.miniblog.back.query.dto.internal.CommentDTO;
import com.miniblog.back.query.dto.internal.LikeHistoryDTO;
import com.miniblog.back.query.util.SortType;
import com.miniblog.back.viewcount.model.QViewCount;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class QueryRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    private final QPost post = QPost.post;
    private final QPostAggregate postAggregate = QPostAggregate.postAggregate;
    private final QViewCount viewCount = QViewCount.viewCount;
    private final QComment comment = QComment.comment;
    private final QLikeHistory likeHistory = QLikeHistory.likeHistory;

    private JPAQuery<PostDetailDTO> basePostDetailQuery() {
        return queryFactory
                .select(Projections.constructor(
                        PostDetailDTO.class,
                        post.id,
                        post.title,
                        post.content,
                        Projections.constructor(
                                MemberInfoDTO.class,
                                post.member.id,
                                post.member.nickname
                        ),
                        post.createdDate,
                        post.updatedDate,
                        viewCount.count.coalesce(0L),
                        postAggregate.comment_count.coalesce(0L),
                        postAggregate.like_count.coalesce(0L)
                ))
                .from(post)
                .leftJoin(viewCount).on(viewCount.post.id.eq(post.id))
                .leftJoin(postAggregate).on(postAggregate.post.id.eq(post.id))
                .groupBy(
                        post.id,
                        post.title,
                        post.content,
                        post.member.nickname,
                        post.createdDate,
                        post.updatedDate,
                        viewCount.count,
                        postAggregate.comment_count,
                        postAggregate.like_count
                );
    }

    public Optional<PostDetailDTO> getPostDetail(Long postId) {
        return Optional.ofNullable(
                basePostDetailQuery()
                        .where(post.id.eq(postId))
                        .fetchOne()
        );
    }

    public List<PostDetailDTO> getPostDetailListByIds(List<Long> postIds) {
        if (postIds.isEmpty()) {
            return Collections.emptyList();
        }
        JPAQuery<PostDetailDTO> baseQuery = basePostDetailQuery();

        return baseQuery
                .where(post.id.in(postIds))
                .fetch();
    }

    private OrderSpecifier<?>[] getSortCondition(SortType type) {
        return switch (type) {
            case LATEST -> new OrderSpecifier[]{post.createdDate.desc()};
            case VIEWS -> new OrderSpecifier[]{viewCount.count.desc(), post.id.desc()};
            case LIKES -> new OrderSpecifier[]{postAggregate.like_count.desc(), post.id.desc()};
        };
    }

    private BooleanExpression eqMemberId(Long memberId) {
        if (memberId == null || memberId <= 0) {
            return null;
        }
        return post.member.id.eq(memberId);
    }

    public List<PostDetailDTO> getPostDetailListByCondition(Long memberId, int page, int limitCount, SortType type) {
        int offset = page * limitCount;

        JPAQuery<PostDetailDTO> baseQuery = basePostDetailQuery();

        BooleanExpression memberIdCondition = eqMemberId(memberId);
        if (memberIdCondition != null) {
            baseQuery.where(memberIdCondition);
        }

        return baseQuery
                .orderBy(getSortCondition(type))
                .offset(offset)
                .limit(limitCount)
                .fetch();
    }



    public List<LikeHistoryDTO> getLikeHistoriesByPostId(Long postId) {
        return queryFactory
                .select(Projections.constructor(
                        LikeHistoryDTO.class,
                        likeHistory.id,
                        Projections.constructor(
                                MemberInfoDTO.class,
                                likeHistory.member.id,
                                likeHistory.member.nickname
                        ),
                        likeHistory.createdDate
                ))
                .from(likeHistory)
                .where(likeHistory.post.id.eq(postId))
                .fetch();
    }

    public List<CommentDTO> getCommentsByPostId(Long postId) {
        return queryFactory
                .select(Projections.constructor(
                        CommentDTO.class,
                        comment.id,
                        Projections.constructor(
                                MemberInfoDTO.class,
                                comment.member.id,
                                comment.member.nickname
                        ),
                        comment.content,
                        comment.createdDate,
                        comment.updatedDate
                ))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .fetch();
    }

    public List<Long> findLikeHistoriesByMemberId(Long memberId, int page, int limitCount, SortType type) {
        int offset = page * limitCount;

        return queryFactory
                .select(likeHistory.post.id)
                .distinct()
                .from(likeHistory)
                .where(likeHistory.member.id.eq(memberId))
                .groupBy(likeHistory.post.id)
                .orderBy(getSortCondition(type))
                .offset(offset)
                .limit(limitCount)
                .fetch();
    }

    public List<Long> findCommentsByMemberId(Long memberId, int page, int limitCount, SortType type) {
        int offset = page * limitCount;

        return queryFactory
                .select(comment.post.id)
                .distinct()
                .from(comment)
                .where(comment.member.id.eq(memberId))
                .groupBy(comment.post.id)
                .orderBy(getSortCondition(type))
                .offset(offset)
                .limit(limitCount)
                .fetch();
    }

    public List<PostDetailDTO> searchWithFullTestAndCondition(String keyword, Long memberId, int page, int limitCount) {
        int offset = page * limitCount;

        String nativeSql = """
            SELECT p.id
              FROM posts p
             WHERE MATCH(p.title, p.content) AGAINST (:keyword IN NATURAL LANGUAGE MODE)
             ORDER BY MATCH(p.title, p.content) AGAINST (:keyword IN NATURAL LANGUAGE MODE) DESC
             LIMIT :limit OFFSET :offset
        """;

        @SuppressWarnings("unchecked")
        List<Long> matchedPostIds = entityManager
                .createNativeQuery(nativeSql)
                .setParameter("keyword", keyword)
                .setParameter("limit", limitCount)
                .setParameter("offset", offset)
                .getResultList();

        if (matchedPostIds.isEmpty()) {
            return Collections.emptyList();
        }

        JPAQuery<PostDetailDTO> baseQuery = basePostDetailQuery();

        BooleanExpression memberIdCondition = eqMemberId(memberId);
        if (memberIdCondition != null) {
            baseQuery.where(memberIdCondition);
        }

        return baseQuery.where(post.id.in(matchedPostIds)).fetch();
    }
}
