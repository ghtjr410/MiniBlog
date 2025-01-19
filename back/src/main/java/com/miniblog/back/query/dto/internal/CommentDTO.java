package com.miniblog.back.query.dto.internal;

import java.time.LocalDateTime;

public record CommentDTO(
        Long id,
        MemberInfoDTO author,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
