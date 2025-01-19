package com.miniblog.back.query.controller;

import com.miniblog.back.query.dto.internal.PostDetailDTO;
import com.miniblog.back.query.dto.response.PostDetailResponseDTO;
import com.miniblog.back.query.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/query/posts")
@RequiredArgsConstructor
public class QueryController {
    private final QueryService queryService;

    // 상세 게시글
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDTO> getPostDetail(
            @PathVariable Long postId
    ) {
        PostDetailResponseDTO responseDTO = queryService.getPostDetail(postId);
        return ResponseEntity.ok(responseDTO);
    }

    // 최신순 게시글 40개씩 페이징
    @GetMapping("/latest")
    public ResponseEntity<List<PostDetailDTO>> getPostsByNickname(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByLatestByMemberId(page);
        return ResponseEntity.ok(responseDTO);
    }

    // 조회수순 게시글 40개씩 페이징
    @GetMapping("/views")
    public ResponseEntity<List<PostDetailDTO>> getPostsByViews(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByViewsByMemberId(page);
        return ResponseEntity.ok(responseDTO);
    }

    // 좋아요순 게시글 40개씩 페이징
    @GetMapping("/likes")
    public ResponseEntity<List<PostDetailDTO>> getPostsByLikes(
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByLikes(page);
        return ResponseEntity.ok(responseDTO);
    }

    // 닉네임 + 최신순 게시글 20개씩 페이징
    @GetMapping("/members/{memberId}/latest")
    public ResponseEntity<List<PostDetailDTO>> getPostsByLatest(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByLatestByMemberId(memberId, page);
        return ResponseEntity.ok(responseDTO);
    }

    // MemberId 기준 조회수순 게시글 20개씩 페이징
    @GetMapping("/members/{memberId}/views")
    public ResponseEntity<List<PostDetailDTO>> getPostsByViewsByMemberId(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByViewsByMemberId(memberId, page);
        return ResponseEntity.ok(responseDTO);
    }

    // MemberId 기준 좋아요순 게시글 20개씩 페이징
    @GetMapping("/members/{memberId}/likes")
    public ResponseEntity<List<PostDetailDTO>> getPostsByLikesByMemberId(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getPostsByLikes(memberId, page);
        return ResponseEntity.ok(responseDTO);
    }

    // MemberId 기준 좋아요누른 게시글 목록 20개씩 페이징
    @GetMapping("/members/{memberId}/liked-posts")
    public ResponseEntity<List<PostDetailDTO>> getLikeHistoriesByMemberId(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getLikedPostsByMemberId(memberId, page);
        return ResponseEntity.ok(responseDTO);
    }

    // MemberId 기준 댓글작성한 게시글 목록 20개씩 페이징
    @GetMapping("/members/{memberId}/commented-posts")
    public ResponseEntity<List<PostDetailDTO>> getCommentsByMemberId(
            @PathVariable("memberId") Long memberId,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.getCommentedPostsByMemberId(memberId, page);
        return ResponseEntity.ok(responseDTO);
    }

    // FullText 검색 WITH PARSER ngram
    @GetMapping("/search/fulltext")
    public ResponseEntity<List<PostDetailDTO>> searchFullTextPosts(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.searchFullTextPostsByMemberId(keyword, page);
        return ResponseEntity.ok(responseDTO);
    }

    // MemberId 기준 FullText 검색 WITH PARSER ngram
    @GetMapping("/members/{memberId}/search/fulltext")
    public ResponseEntity<List<PostDetailDTO>> searchFullTextPostsByMemberId(
            @PathVariable("memberId") Long memberId,
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        List<PostDetailDTO> responseDTO = queryService.searchFullTextPostsByMemberId(keyword, memberId, page);
        return ResponseEntity.ok(responseDTO);
    }
}
