package com.miniblog.api.query.infrastructure;

import com.miniblog.api.query.dto.common.SearchCondition;
import com.miniblog.api.query.dto.query.AuthorDto;
import com.miniblog.api.query.dto.query.PostCardDto;
import com.miniblog.api.query.dto.query.QAuthorDto;
import com.miniblog.api.query.dto.query.QPostCardDto;
import com.miniblog.api.query.util.SliceExecutionUtils;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.miniblog.api.post.domain.QPost.post;
import static com.miniblog.api.post.domain.QPostCounts.postCounts;
import static com.miniblog.api.post.domain.QPostViews.postViews;

@Repository
@RequiredArgsConstructor
public class PostSearchQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public Slice<PostCardDto> searchAllWithFullText(SearchCondition condition, Pageable pageable) {
        int offset = pageable.getPageNumber() * pageable.getPageSize();
        int size = pageable.getPageSize();

        String nativeSql = """
                SELECT
                    p.post_id,
                    p.title,
                    p.content_summary,
                    m.member_id AS author_id,
                    m.nickname AS author_nickname,
                    pv.view_count,
                    pc.comment_count,
                    pc.like_count,
                    p.created_date,
                    p.updated_date
                FROM posts p
                JOIN members m ON p.member_id = m.member_id
                JOIN post_views pv ON p.post_id = pv.post_id
                JOIN post_counts pc ON p.post_id = pc.post_id
                WHERE MATCH(p.title, p.content_plain) AGAINST (:keyword IN NATURAL LANGUAGE MODE)
                ORDER BY MATCH(p.title, p.content_plain) AGAINST (:keyword IN NATURAL LANGUAGE MODE) DESC,
                         p.created_date DESC
                LIMIT :limit OFFSET :offset
                """;

        @SuppressWarnings("unchecked")
        List<Object[]> resultList = em
                .createNativeQuery(nativeSql)
                .setParameter("keyword", condition.getKeyword())
                .setParameter("limit", SliceExecutionUtils.buildSliceLimit(size))
                .setParameter("offset", offset)
                .getResultList();

        List<PostCardDto> content = resultList.stream()
                .map(row -> new PostCardDto(
                        (Long) row[0],  // postId
                        (String) row[1],  // title
                        (String) row[2],  // contentSummary
                        new AuthorDto((Long) row[3], (String) row[4]),  // authorId, nickname
                        (Long) row[5],  // viewCount
                        (Long) row[6],  // commentCount
                        (Long) row[7],  // likeCount
                        ((Timestamp) row[8]).toLocalDateTime(),  // createdDate
                        ((Timestamp) row[9]).toLocalDateTime()   // updatedDate
                )).collect(Collectors.toList());

        return SliceExecutionUtils.getSlice(content, size);
    }

    public Slice<PostCardDto> searchByMemberWithLike(SearchCondition condition, Long memberId, Pageable pageable) {

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
                        post.member.id.eq(memberId),
                        post.title.containsIgnoreCase(condition.getKeyword())
                                .or(post.contentPlain.containsIgnoreCase(condition.getKeyword()))
                )
                .orderBy(post.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(SliceExecutionUtils.buildSliceLimit(pageable.getPageSize()))
                .fetch();

        return SliceExecutionUtils.getSlice(content, pageable.getPageSize());
    }
}
