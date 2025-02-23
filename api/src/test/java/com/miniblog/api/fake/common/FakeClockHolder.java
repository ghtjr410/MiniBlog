package com.miniblog.api.fake.common;

import com.miniblog.api.common.application.port.ClockHolder;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class FakeClockHolder implements ClockHolder {

    private final LocalDateTime now;

    @Override
    public LocalDateTime now() {
        return now;
    }
}
