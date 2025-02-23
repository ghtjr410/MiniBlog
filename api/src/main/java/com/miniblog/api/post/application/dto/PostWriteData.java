package com.miniblog.api.post.application.dto;

import com.miniblog.api.post.api.dto.request.PostWriteRequest;

public record PostWriteData(
        String title,
        String content,
        Long memberId
) {
    public static PostWriteData of(PostWriteRequest request, long memberId) {
        return new PostWriteData(request.title(), request.content(), memberId);
    }
}
