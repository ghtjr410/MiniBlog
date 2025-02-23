package com.miniblog.api.common.infrastructure.config;

import com.miniblog.api.auth.application.dto.MemberInfoData;
import com.miniblog.api.auth.application.port.SecurityContextManager;
import com.miniblog.api.auth.application.port.SecurityExtractor;
import com.miniblog.api.auth.application.port.TokenParser;
import com.miniblog.api.auth.application.support.TokenFinder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final SecurityProperties securityProperties;
    private final SecurityExtractor securityExtractor;
    private final TokenParser tokenParser;
    private final SecurityContextManager securityContextManager;
    private final TokenFinder tokenReader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getServletPath();
        log.info("요청 URI: {}", requestURI);

        if (securityProperties.isPermitted(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String extractedToken = securityExtractor.extractToken(authHeader);

        tokenReader.ensureNotBlacklisted(extractedToken);
        MemberInfoData memberInfo = tokenParser.extractMemberInfo(extractedToken);

        securityContextManager.setAuthentication(memberInfo);

        filterChain.doFilter(request, response);
    }
}
