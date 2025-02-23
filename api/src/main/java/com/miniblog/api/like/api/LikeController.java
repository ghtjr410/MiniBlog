package com.miniblog.api.like.api;

import com.miniblog.api.common.api.MemberId;
import com.miniblog.api.like.application.LikeToggleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class LikeController {

    private final LikeToggleService likeToggleService;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> likePost(
            @PathVariable long postId,
            @MemberId Long memberId
    ) {
        likeToggleService.likePost(postId, memberId);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{postId}/likes")
    public ResponseEntity<Void> unlikePost(
            @PathVariable long postId,
            @MemberId Long memberId
    ) {
        likeToggleService.unlikePost(postId, memberId);

        return ResponseEntity.noContent().build();
    }
}
