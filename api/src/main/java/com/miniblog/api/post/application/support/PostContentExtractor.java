package com.miniblog.api.post.application.support;

import com.miniblog.api.common.application.port.HtmlTextExtractor;
import com.miniblog.api.post.application.dto.ContentExtractResult;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 게시글 본문 내용에서 텍스트를 추출하는 컴포넌트
 * - HTML 태그를 제거하고 요약 및 일반 텍스트 버전을 생성
 */
@Component
@RequiredArgsConstructor
public class PostContentExtractor {

    private final HtmlTextExtractor htmlTextExtractor;

    /**
     * HTML 본문을 분석하여 요약 및 일반 텍스트 버전 생성
     */
    public ContentExtractResult extractHtml(String content) {
        String summary = htmlTextExtractor.extractSummary(content);
        String plain = htmlTextExtractor.extractFullText(content);

        return ContentExtractResult.of(summary, plain);
    }

    /**
     * 기존 게시글과 새로운 본문을 비교하여 변경된 경우만 본문을 재추출
     */
    public ContentExtractResult extractHtmlIfChanged(Post post, String newContent) {
        if (post.hasContentChanged(newContent)) {
            return extractHtml(newContent);
        }
        return ContentExtractResult.of(post.getContentSummary(), post.getContentPlain());
    }
}
