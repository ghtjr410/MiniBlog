package com.miniblog.back.query.dto.internal;

import java.time.LocalDateTime;

public record PostDetailDTO(
        Long id,
        String title,
        String content,
        MemberInfoDTO author,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        Long viewCount,
        Long commentCount,
        Long likeCount
) {
}
