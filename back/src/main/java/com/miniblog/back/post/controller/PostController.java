package com.miniblog.back.post.controller;

import com.miniblog.back.post.dto.request.PostCreateRequestDTO;
import com.miniblog.back.post.dto.request.PostUpdateRequestDTO;
import com.miniblog.back.post.dto.response.PostCreatedResponseDTO;
import com.miniblog.back.post.dto.response.PostUpdatedResponseDTO;
import com.miniblog.back.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostCreatedResponseDTO> create(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody PostCreateRequestDTO requestDTO
    ) {
        PostCreatedResponseDTO responseDTO = postService.create(authorizationHeader, requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostUpdatedResponseDTO> update(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long postId,
            @Valid @RequestBody PostUpdateRequestDTO requestDTO
    ) {
        PostUpdatedResponseDTO responseDTO = postService.update(authorizationHeader, requestDTO, postId);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long postId
    ) {
        postService.delete(authorizationHeader, postId);
        return ResponseEntity.noContent().build();
    }

}
