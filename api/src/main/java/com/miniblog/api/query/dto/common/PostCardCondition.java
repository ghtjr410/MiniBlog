package com.miniblog.api.query.dto.common;

import com.miniblog.api.query.util.SortType;
import com.miniblog.api.query.util.TimeRange;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor
public class PostCardCondition {

    @NotNull(message = "시간 범위는 필수입니다.")
    private TimeRange timeRange;

    @NotNull(message = "정렬 방식은 필수입니다.")
    private SortType sortType;

    @Setter
    @Min(value = 0, message = "페이지 번호는 0 이상이어야 합니다.")
    private int page = 0;

    @Setter
    @NotNull(message = "페이지 크기는 필수입니다.")
    @Min(value = 20, message = "최소 페이지 크기는 20이어야 합니다.")
    @Max(value = 40, message = "최대 페이지 크기는 40이어야 합니다.")
    private Integer size = 40;

    public PostCardCondition(TimeRange timeRange, SortType sortType, int page, int size) {
        this.timeRange = timeRange;
        this.sortType = sortType;
        this.page = page;
        this.size = size;
    }

    public void setTime_range(String timeRangeParam) {
        // ex: last_week -> TimeRange.LAST_WEEK
        this.timeRange = TimeRange.fromString(timeRangeParam);
    }

    public void setSort_type(String sortTypeParam) {
        // ex: latest -> SortType.LATEST
        this.sortType = SortType.fromString(sortTypeParam);
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
