package com.miniblog.back.auth.filter;

import com.miniblog.back.auth.config.SecurityProperties;
import com.miniblog.back.auth.service.AuthService;
import com.miniblog.back.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final SecurityProperties securityProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean isPermitted = securityProperties.getPermitAllUrls()
                .stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestURI));

        if (isPermitted) {
            log.info("1. 요청 도착 : {}, permitAll list : {}", requestURI, securityProperties.getPermitAllUrls());
            filterChain.doFilter(request, response);
            return;
        }
        log.info("1. 요청 도착 : {}", requestURI);

        String authorizationHeader = request.getHeader("Authorization");
        log.info("2. 토큰 도착 : {}",authorizationHeader);

        tokenService.validateToken(authorizationHeader);

        Authentication authentication = tokenService.getAuthenticationFromToken(authorizationHeader);
        log.info("10. authentication : {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
