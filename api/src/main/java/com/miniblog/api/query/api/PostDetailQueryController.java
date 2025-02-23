package com.miniblog.api.query.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.query.application.CommentDetailQueryService;
import com.miniblog.api.query.application.PostDetailQueryService;
import com.miniblog.api.query.dto.common.CommentCondition;
import com.miniblog.api.query.dto.query.CommentDetailDto;
import com.miniblog.api.query.dto.query.PostDetailWithCommentDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/query")
@RequiredArgsConstructor
public class PostDetailQueryController {

    private final PostDetailQueryService postDetailQueryService;
    private final CommentDetailQueryService commentDetailQueryService;

    /**
     * 특정 게시글의 상세 정보 + 첫 번째 댓글 페이지 조회
     * - 댓글은 특정 게시글에 한정된 자원이므로, Page 기반 페이징 방식 사용
     */
    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDetailWithCommentDto> getPostDetailWithComments(
            @PathVariable long postId
    ) {
        Pageable pageable = CommentCondition.toPageableByFirstRequest();
        PostDetailWithCommentDto result = postDetailQueryService.getPostDetailWithComments(postId, pageable);
        return ApiResponse.success(result);
    }

    /**
     * 댓글 조회 조건
     *
     * - 기본 정렬: 최신순 (created_date DESC)
     * - 페이징 설정:
     *   - 페이지 번호: 0부터 시작
     *   - 최소 페이지 크기: 20개
     *   - 최대 페이지 크기: 40개
     */

    /**
     * 특정 게시글의 댓글 목록을 Page 방식으로 조회 (정확한 페이징 지원)
     * - `Page<T>` 형태로 반환
     * - 전체 댓글 개수 포함
     */
    @GetMapping("/posts/{postId}/comments")
    public ApiResponse<Page<CommentDetailDto>> getCommentsByPostId(
            @PathVariable long postId,
            @ModelAttribute @Valid CommentCondition condition
    ) {
        Page<CommentDetailDto> comments = commentDetailQueryService.getCommentsByPostId(postId, condition.toPageable());
        return ApiResponse.success(comments);
    }
}
