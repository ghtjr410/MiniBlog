package com.miniblog.back.query.dto.internal;

import java.time.LocalDateTime;

public record LikeHistoryDTO(
        Long id,
        MemberInfoDTO author,
        LocalDateTime createdDate
) {
}
