package com.miniblog.api.post.application.dto;

public record ContentExtractResult(
        String summary,
        String plain
) {
    public static ContentExtractResult of(String summary, String plain) {
        return new ContentExtractResult(summary, plain);
    }
}
