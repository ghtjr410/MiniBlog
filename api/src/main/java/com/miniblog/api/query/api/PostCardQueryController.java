package com.miniblog.api.query.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.query.application.PostCardQueryService;
import com.miniblog.api.query.dto.common.PostCardCondition;
import com.miniblog.api.query.dto.query.PostCardDto;
import com.miniblog.api.query.dto.response.NoCountSliceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/query")
@RequiredArgsConstructor
public class PostCardQueryController {

    private final PostCardQueryService postCardQueryService;

    /**
     * 게시글 카드 조회 조건
     *
     * - 정렬 방식(SortType) 지원 목록:
     *   1. 최신순 (SortType.LATEST)
     *   2. 조회순 (SortType.VIEWS)
     *   3. 좋아요순 (SortType.LIKES)
     *
     * - 시간 범위(TimeRange) 지원 목록:
     *   1. 최근 1일 (TimeRange.LAST_DAY)
     *   2. 최근 1주일 (TimeRange.LAST_WEEK)
     *   3. 최근 1개월 (TimeRange.LAST_MONTH)
     *   4. 전체 기간 (TimeRange.ALL)
     *
     * - 페이징 설정:
     *   - 페이지 번호: 0부터 시작
     *   - 최소 페이지 크기: 20개
     *   - 최대 페이지 크기: 40개
     */

    /**
     * 전체 게시글 목록을 Slice 방식으로 조회 (무한 스크롤 지원)
     * - `SliceResponse<T>` 형태로 반환
     * - 전체 개수 조회 없이, 다음 페이지 여부만 확인
     */
    @GetMapping("/posts")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> getAllPostCards(
            @ModelAttribute @Valid PostCardCondition condition
    ) {
        Slice<PostCardDto> slice = postCardQueryService.getAllPostCards(condition, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(slice));
    }

    /**
     * 특정 사용자의 게시글 목록을 Slice 방식으로 조회 (무한 스크롤 지원)
     */
    @GetMapping("/members/{memberId}/posts")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> getMemberPostCards(
            @PathVariable long memberId,
            @ModelAttribute @Valid PostCardCondition condition
    ) {
        Slice<PostCardDto> slice = postCardQueryService.getMemberPostCards(condition, memberId, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(slice));
    }

    /**
     * 특정 사용자가 댓글을 작성한 게시글 목록을 Slice 방식으로 조회
     */
    @GetMapping("/members/{memberId}/commented-posts")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> getAllOrMemberPostCards1(
            @PathVariable long memberId,
            @ModelAttribute @Valid PostCardCondition condition
    ) {
        Slice<PostCardDto> slice = postCardQueryService.getCommentedPostCards(condition, memberId, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(slice));
    }

    /**
     * 특정 사용자가 좋아요한 게시글 목록을 Slice 방식으로 조회
     */
    @GetMapping("/members/{memberId}/liked-posts")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> getAllOrMemberPostCards2(
            @PathVariable long memberId,
            @ModelAttribute @Valid PostCardCondition condition
    ) {
        Slice<PostCardDto> slice = postCardQueryService.getLikedPostCards(condition, memberId, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(slice));
    }
}
