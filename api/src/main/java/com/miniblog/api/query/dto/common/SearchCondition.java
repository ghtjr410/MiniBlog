package com.miniblog.api.query.dto.common;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Getter @Setter
@NoArgsConstructor
public class SearchCondition {

    @NotBlank
    @Size(min = 2)
    private String keyword;

    @Min(value = 0, message = "페이지 번호는 0이상이어야 합니다.")
    private int page = 0;

    @NotNull(message = "페이지 크기는 필수입니다.")
    @Min(value = 20, message = "최소 페이지 크기는 20이어야 합니다.")
    @Max(value = 40, message = "최대 페이지 크기는 40이어야 합니다.")
    private Integer size = 20;

    public SearchCondition(int page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
