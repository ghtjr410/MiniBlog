package com.miniblog.api.comment.api;

import com.miniblog.api.comment.api.dto.request.CommentEditRequest;
import com.miniblog.api.comment.api.dto.request.CommentWriteRequest;
import com.miniblog.api.comment.application.business.CommentDeleteService;
import com.miniblog.api.comment.application.business.CommentEditService;
import com.miniblog.api.comment.application.business.CommentWriteService;
import com.miniblog.api.comment.application.dto.CommentEditData;
import com.miniblog.api.comment.application.dto.CommentWriteData;
import com.miniblog.api.comment.application.support.CommentReader;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.common.api.MemberId;
import com.miniblog.api.query.dto.query.CommentDetailDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentWriteService commentWriteService;
    private final CommentEditService commentEditService;
    private final CommentDeleteService commentDeleteService;
    private final CommentReader commentReader;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> writeComment(
            @Valid @RequestBody CommentWriteRequest request,
            @MemberId Long memberId
    ) {
        CommentWriteData data = CommentWriteData.of(request, memberId);

        long commentId = commentWriteService.addComment(data);

        Comment comment = commentReader.getByIdWithMemberOrThrow(commentId);
        CommentDetailDto detail = CommentDetailDto.from(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(detail));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponse<?>> editComment(
            @PathVariable long commentId,
            @Valid @RequestBody CommentEditRequest request,
            @MemberId Long memberId
    ) {
        CommentEditData data = CommentEditData.of(request, commentId, memberId);

        commentEditService.editComment(data);

        Comment comment = commentReader.getByIdWithMemberOrThrow(commentId);
        CommentDetailDto detail = CommentDetailDto.from(comment);

        return ResponseEntity.ok(ApiResponse.success(detail));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable long commentId,
            @MemberId Long memberId
    ) {
        commentDeleteService.deleteComment(commentId, memberId);

        return ResponseEntity.noContent().build();
    }
}