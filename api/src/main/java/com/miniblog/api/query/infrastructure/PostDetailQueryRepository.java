package com.miniblog.api.query.infrastructure;

import com.miniblog.api.query.dto.query.PostDetailDto;
import com.miniblog.api.query.dto.query.QAuthorDto;
import com.miniblog.api.query.dto.query.QPostDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.miniblog.api.post.domain.QPost.post;
import static com.miniblog.api.post.domain.QPostCounts.postCounts;
import static com.miniblog.api.post.domain.QPostViews.postViews;

@Repository
@RequiredArgsConstructor
public class PostDetailQueryRepository {

    private final JPAQueryFactory query;

    public Optional<PostDetailDto> getPostDetail(Long postId){
        return Optional.ofNullable(
                query
                        .select(new QPostDetailDto(
                                post.id,
                                post.title,
                                post.contentPlain,
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
                        .join(postViews).on(postViews.post.id.eq(postId))
                        .join(postCounts).on(postCounts.post.id.eq(postId))
                        .where(post.id.eq(postId))
                        .fetchOne());
    }
}
