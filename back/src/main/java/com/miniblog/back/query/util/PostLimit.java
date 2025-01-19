package com.miniblog.back.query.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PostLimit {
    SMALL(20),
    DEFAULT(40),
    LARGE(100);

    private final int value;
}
