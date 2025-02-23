package com.miniblog.api.query.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToTimeRangeConverter implements Converter<String, TimeRange> {
    @Override
    public TimeRange convert(String source) {
        return TimeRange.fromString(source);
    }
}
