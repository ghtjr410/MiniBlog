package com.miniblog.back.auth.filter;

import com.miniblog.back.auth.config.SecurityProperties;
import com.miniblog.back.auth.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomTokenFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final SecurityProperties securityProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        log.info("요청 경로: {}",requestURI);
        if (securityProperties.getPermitAllUrls().contains(requestURI)) {
            log.info("PermitAllUrls 확인 통과");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("PermitAllUrls 미포함 인증 및 검증 시작");

        String authorizationHeader = request.getHeader("Authorization");
        if (!authService.validateToken(authorizationHeader)) {
            log.info("토큰 검증 실패");
            throw new SecurityException("Invalid or blacklisted token");
        }
        log.info("토큰 검증 성공");

        // 토큰 검증 성공 시 SecurityContext에 인증 정보 설정
        Authentication authentication = authService.getAuthenticationFromToken(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("인증 성공 및 SecurityContext 설정 완료");
        filterChain.doFilter(request, response);
        log.info("필터 체인 이후 로직 실행 완료");
    }
}
