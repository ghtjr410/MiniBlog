package com.miniblog.api.common.application.port;

public interface HtmlTextExtractor {
    String extractSummary(String htmlContent);
    String extractFullText(String htmlContent);
}
