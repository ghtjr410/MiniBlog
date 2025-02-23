package com.miniblog.api.query.infrastructure;

import com.miniblog.api.query.dto.common.PostCardCondition;
import com.miniblog.api.query.dto.query.PostCardDto;
import com.miniblog.api.query.dto.query.QAuthorDto;
import com.miniblog.api.query.dto.query.QPostCardDto;
import com.miniblog.api.query.util.SliceExecutionUtils;
import com.miniblog.api.query.util.SortType;
import com.miniblog.api.query.util.TimeRange;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.miniblog.api.comment.domain.QComment.comment;
import static com.miniblog.api.like.domain.QLikeHistory.likeHistory;
import static com.miniblog.api.post.domain.QPost.post;
import static com.miniblog.api.post.domain.QPostCounts.postCounts;
import static com.miniblog.api.post.domain.QPostViews.postViews;

@Repository
@RequiredArgsConstructor
public class PostCardQueryRepository {

    private final JPAQueryFactory query;

    public Slice<PostCardDto> getAllOrMemberPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        List<PostCardDto> content = query
                .select(new QPostCardDto(
                        post.id,
                        post.title,
                        post.contentSummary,
                        new QAuthorDto(
                                post.member.id,
                                post.member.nickname
                        ),
                        postViews.viewCount,
                        postCounts.commentCount,
                        postCounts.likeCount,
                        post.createdDate,
                        post.updatedDate
                ))
                .from(post)
                .join(postViews).on(postViews.post.id.eq(post.id))
                .join(postCounts).on(postCounts.post.id.eq(post.id))
                .where(
                        memberIdEq(memberId),
                        createdAfter(condition.getTimeRange())
                )
                .orderBy(sortDesc(condition.getSortType()))
                .offset(pageable.getOffset())
                .limit(SliceExecutionUtils.buildSliceLimit(pageable.getPageSize()))
                .fetch();

        return SliceExecutionUtils.getSlice(content, pageable.getPageSize());
    }

    public Slice<PostCardDto> getCommentedPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        List<PostCardDto> content = query
                .selectDistinct(new QPostCardDto(
                        post.id,
                        post.title,
                        post.contentSummary,
                        new QAuthorDto(
                                post.member.id,
                                post.member.nickname
                        ),
                        postViews.viewCount,
                        postCounts.commentCount,
                        postCounts.likeCount,
                        post.createdDate,
                        post.updatedDate
                ))
                .from(post)
                .join(postViews).on(postViews.post.id.eq(post.id))
                .join(postCounts).on(postCounts.post.id.eq(post.id))
                .join(comment).on(comment.post.id.eq(post.id))
                .where(
                        comment.member.id.eq(memberId),
                        createdAfter(condition.getTimeRange())
                )
                .orderBy(sortDesc(condition.getSortType()))
                .offset(pageable.getOffset())
                .limit(SliceExecutionUtils.buildSliceLimit(pageable.getPageSize()))
                .fetch();

        return SliceExecutionUtils.getSlice(content, pageable.getPageSize());
    }

    public Slice<PostCardDto> getLikedPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        List<PostCardDto> content = query
                .select(new QPostCardDto(
                        post.id,
                        post.title,
                        post.contentSummary,
                        new QAuthorDto(
                                post.member.id,
                                post.member.nickname
                        ),
                        postViews.viewCount,
                        postCounts.commentCount,
                        postCounts.likeCount,
                        post.createdDate,
                        post.updatedDate
                ))
                .from(post)
                .join(postViews).on(postViews.post.id.eq(post.id))
                .join(postCounts).on(postCounts.post.id.eq(post.id))
                .join(likeHistory).on(likeHistory.post.id.eq(post.id))
                .where(
                        likeHistory.member.id.eq(memberId),
                        createdAfter(condition.getTimeRange())
                )
                .orderBy(sortDesc(condition.getSortType()))
                .offset(pageable.getOffset())
                .limit(SliceExecutionUtils.buildSliceLimit(pageable.getPageSize()))
                .fetch();

        return SliceExecutionUtils.getSlice(content, pageable.getPageSize());
    }


    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? post.member.id.eq(memberId) : null;
    }

    private BooleanExpression createdAfter(TimeRange timeRange) {
        LocalDateTime now = LocalDateTime.now();

        return switch (timeRange) {
            case LAST_DAY -> post.createdDate.after(now.minusDays(1));
            case LAST_WEEK -> post.createdDate.after(now.minusWeeks(1));
            case LAST_MONTH -> post.createdDate.after(now.minusMonths(1));
            case ALL -> null;
        };
    }

    private OrderSpecifier<?>[] sortDesc(SortType type) {
        return switch (type) {
            case LATEST -> new OrderSpecifier[]{post.createdDate.desc()};
            case VIEWS -> new OrderSpecifier[]{postViews.viewCount.desc(), post.id.desc()};
            case LIKES -> new OrderSpecifier[]{postCounts.likeCount.desc(), post.id.desc()};
        };
    }
}
