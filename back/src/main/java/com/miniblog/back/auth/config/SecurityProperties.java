package com.miniblog.back.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private List<String> permitAllUrls;
    private List<String> authenticatedUrls;
    private List<String> userOnlyUrls;

    public String[] getPermitAllUrlsAsArray() {
        return permitAllUrls.toArray(new String[0]);
    }

    public String[] getAuthenticatedUrlsAsArray() {
        return authenticatedUrls.toArray(new String[0]);
    }

    public String[] getUserOnlyUrlsAsArray() {
        return userOnlyUrls.toArray(new String[0]);
    }
}
