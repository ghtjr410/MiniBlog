package com.miniblog.api.post.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.common.api.MemberId;
import com.miniblog.api.post.api.dto.request.PostWriteRequest;
import com.miniblog.api.post.api.dto.request.PostEditRequest;
import com.miniblog.api.post.api.dto.response.PostWriteResponse;
import com.miniblog.api.post.api.dto.response.PostEditResponse;
import com.miniblog.api.post.application.business.PostDeleteService;
import com.miniblog.api.post.application.business.PostEditService;
import com.miniblog.api.post.application.business.PostWriteService;
import com.miniblog.api.post.application.dto.PostWriteData;
import com.miniblog.api.post.application.dto.PostEditData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostWriteService postWriteService;
    private final PostEditService postEditService;
    private final PostDeleteService postDeleteService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody PostWriteRequest request,
            @MemberId Long memberId
    ) {
        PostWriteData data = PostWriteData.of(request, memberId);

        long postId = postWriteService.addPost(data);

        PostWriteResponse response = PostWriteResponse.of(postId);
        return ResponseEntity.status(CREATED).body(ApiResponse.success(response));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<?>> update(
            @PathVariable long postId,
            @Valid @RequestBody PostEditRequest request,
            @MemberId Long memberId

    ) {
        PostEditData data = PostEditData.of(postId, request, memberId);

        postEditService.editPost(data);

        PostEditResponse response = PostEditResponse.of(postId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(
            @PathVariable long postId,
            @MemberId Long memberId
    ) {
        postDeleteService.deletePost(postId, memberId);

        return ResponseEntity.noContent().build();
    }
}
