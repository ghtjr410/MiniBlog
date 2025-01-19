package com.miniblog.back.member.email;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EmailCode {
    public static String getCode() {
        return IntStream.range(0, 4)
                .mapToObj(i -> String.valueOf((int) (Math.random() * 10)))
                .collect(Collectors.joining());
    }
}
