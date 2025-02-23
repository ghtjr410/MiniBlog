package com.miniblog.api.query.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
@Getter
public class NoCountSliceResponse<T> {
    private boolean isLast;
    private List<T> content;

    public static <T> NoCountSliceResponse<T> of(Slice<T> slice) {
        return NoCountSliceResponse.<T>builder()
                .isLast(slice.isLast())
                .content(slice.getContent())
                .build();
    }
}
