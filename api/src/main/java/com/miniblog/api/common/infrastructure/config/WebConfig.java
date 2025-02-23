package com.miniblog.api.common.infrastructure.config;

import com.miniblog.api.common.api.MemberIdResolver;
import com.miniblog.api.query.util.StringToSortTypeConverter;
import com.miniblog.api.query.util.StringToTimeRangeConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final MemberIdResolver memberIdResolver;
    private final StringToSortTypeConverter stringToSortTypeConverter;
    private final StringToTimeRangeConverter stringToTimeRangeConverter;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberIdResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(stringToSortTypeConverter);
        registry.addConverter(stringToTimeRangeConverter);
    }
}
