package com.miniblog.api.query.util;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum TimeRange {
    LAST_DAY("last_day"),
    LAST_WEEK("last_week"),
    LAST_MONTH("last_month"),
    ALL("all");

    private final String value;

    TimeRange(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static TimeRange fromString(String text) {
        return Arrays.stream(TimeRange.values())
                .filter(timeRange -> timeRange.value.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid time range: " + text));
    }
}
