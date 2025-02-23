package com.miniblog.api.post.api.dto.response;

public record PostEditResponse(
        long postId
) {
    public static PostEditResponse of(long postId) {
        return new PostEditResponse(postId);
    }
}
