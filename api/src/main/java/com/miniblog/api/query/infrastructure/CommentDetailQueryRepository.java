package com.miniblog.api.query.infrastructure;

import com.miniblog.api.query.dto.query.CommentDetailDto;
import com.miniblog.api.query.dto.query.QAuthorDto;
import com.miniblog.api.query.dto.query.QCommentDetailDto;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.miniblog.api.comment.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentDetailQueryRepository {

    private final JPAQueryFactory query;

    public Page<CommentDetailDto> getCommentsByPostId(Long postId, Pageable pageable) {
        List<CommentDetailDto> content = query
                .select(new QCommentDetailDto(
                        comment.id,
                        comment.content,
                        new QAuthorDto(
                                comment.member.id,
                                comment.member.nickname
                        ),
                        comment.createdDate,
                        comment.updatedDate
                ))
                .from(comment)
                .where(comment.post.id.eq(postId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(comment.count())
                .from(comment)
                .where(comment.post.id.eq(postId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
