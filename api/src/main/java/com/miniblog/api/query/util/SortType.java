package com.miniblog.api.query.util;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SortType {
    LATEST("latest"),
    VIEWS("views"),
    LIKES("likes");

    private final String value;

    SortType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static SortType fromString(String text) {
        return Arrays.stream(SortType.values())
                .filter(sortType -> sortType.value.equalsIgnoreCase(text))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid sort type: " + text));
    }
}
