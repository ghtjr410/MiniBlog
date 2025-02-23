package com.miniblog.api.common.infrastructure;

import com.miniblog.api.common.application.port.HtmlTextExtractor;
import com.miniblog.api.common.domain.exception.ResourceInvalidException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class HtmlTextExtractorImpl implements HtmlTextExtractor {

    private static final int SUMMARY_LENGTH = 100;

    /**
     * HTML에서 텍스트를 추출하여 요약 반환
     * 최대 100자까지만 출력
     */
    public String extractSummary(String htmlContent) {
        String plainText = extractPlainText(htmlContent);
        return plainText.length() > SUMMARY_LENGTH
                ? plainText.substring(0, SUMMARY_LENGTH)
                : plainText;
    }

    /**
     * HTML에서 모든 텍스트를 추출하여 반환
     */
    public String extractFullText(String htmlContent) {
        return extractPlainText(htmlContent);
    }

    /**
     * HTML을 파싱하여 순수한 텍스트만 추출
     * 유효하지 않은 입력인 경우 예외 발생
     */
    private String extractPlainText(String htmlContent) {
        if (htmlContent == null || htmlContent.isBlank()) {
            throw new ResourceInvalidException();
        }
        return Jsoup.parse(htmlContent).text();
    }
}
