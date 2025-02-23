package com.miniblog.api.query.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

@Builder
@Getter
public class SliceResponse<T> {
    private boolean isLast;
    private List<T> content;
    private int count;

    public static <T> SliceResponse<T> of(Slice<T> slice) {
        return SliceResponse.<T>builder()
                .isLast(slice.isLast())
                .content(slice.getContent())
                .count(slice.getNumberOfElements())
                .build();
    }
}
