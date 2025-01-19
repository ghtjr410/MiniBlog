package com.miniblog.back.comment.controller;

import com.miniblog.back.comment.dto.request.CommentCreateRequestDTO;
import com.miniblog.back.comment.dto.request.CommentUpdateRequestDTO;
import com.miniblog.back.comment.dto.response.CommentCreatedResponseDTO;
import com.miniblog.back.comment.dto.response.CommentUpdatedResponseDTO;
import com.miniblog.back.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentCreatedResponseDTO> create(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody CommentCreateRequestDTO requestDTO
    ) {
        CommentCreatedResponseDTO responseDTO = commentService.create(authorizationHeader, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentUpdatedResponseDTO> update(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long commentId,
            @Valid @RequestBody CommentUpdateRequestDTO requestDTO
    ) {
        CommentUpdatedResponseDTO responseDTO = commentService.update(authorizationHeader, requestDTO, commentId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long commentId
    ) {
        commentService.delete(authorizationHeader, commentId);
        return ResponseEntity.noContent().build();
    }
}
