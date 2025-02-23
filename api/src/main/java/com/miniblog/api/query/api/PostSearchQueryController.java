package com.miniblog.api.query.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.query.application.PostSearchQueryService;
import com.miniblog.api.query.dto.common.SearchCondition;
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
public class PostSearchQueryController {

    private final PostSearchQueryService postSearchQueryService;

    /**
     * 게시글 검색 조건
     *
     * - 키워드를 포함한 게시글 검색을 수행
     * - 정렬 방식: Full-Text 검색 점수와 최신순을 조합
     * - 페이징 설정:
     *   - 최소 페이지 크기: 20개
     *   - 최대 페이지 크기: 40개
     */

    /**
     * 전체 게시글을 키워드 기반으로 검색 (Slice 방식, 무한 스크롤 지원)
     * - `SliceResponse<T>` 형태로 반환
     * - 전체 개수 조회 없이, 다음 페이지 여부만 확인
     */
    @GetMapping("/posts/search")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> searchAllWithFullText(
            @ModelAttribute @Valid SearchCondition condition
    ) {

        Slice<PostCardDto> content = postSearchQueryService.searchAllWithFullText(condition, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(content));
    }

    /**
     * 특정 사용자가 작성한 게시글을 키워드 기반으로 검색 (Slice 방식, 무한 스크롤 지원)
     */
    @GetMapping("/members/{memberId}/posts/search")
    public ApiResponse<NoCountSliceResponse<PostCardDto>> searchByMemberWithLike(
            @PathVariable long memberId,
            @ModelAttribute @Valid SearchCondition condition
    ) {

        Slice<PostCardDto> content = postSearchQueryService.searchByMemberWithLike(condition, memberId, condition.toPageable());
        return ApiResponse.success(NoCountSliceResponse.of(content));
    }
}
