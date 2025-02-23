package com.miniblog.api.post.api.dto.response;

public record PostWriteResponse(
        long postId

) {
    public static PostWriteResponse of(long postId) {
        return new PostWriteResponse(postId);
    }
}
