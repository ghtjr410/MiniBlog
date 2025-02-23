package com.miniblog.api.query.application;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.post.application.support.PostStatisticsUpdater;
import com.miniblog.api.query.dto.query.CommentDetailDto;
import com.miniblog.api.query.dto.query.PostDetailDto;
import com.miniblog.api.query.dto.query.PostDetailWithCommentDto;
import com.miniblog.api.query.infrastructure.PostDetailQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.query.util.QueryErrorMessage.POST_DETAIL_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostDetailQueryService {

    private final PostStatisticsUpdater postStatisticsUpdater;
    private final CommentDetailQueryService commentDetailQueryService;
    private final PostDetailQueryRepository postDetailQueryRepository;

    /**
     * 게시글 상세 정보를 조회하고, 댓글 목록을 페이징하여 반환
     * - 댓글 개수가 0이면 댓글 조회 쿼리 실행을 생략
     */

    public PostDetailWithCommentDto getPostDetailWithComments(Long postId, Pageable pageable) {
        PostDetailDto postDetail = postDetailQueryRepository.getPostDetail(postId)
                .orElseThrow(() -> new ResourceNotFoundException(POST_DETAIL_NOT_FOUND));

        Page<CommentDetailDto> comments = (postDetail.getCommentCount() > 0)
                ? commentDetailQueryService.getCommentsByPostId(postId, pageable)
                : Page.empty(pageable);

        postStatisticsUpdater.incrementViewCount(postId);

        return PostDetailWithCommentDto.of(postDetail, comments);
    }
}
