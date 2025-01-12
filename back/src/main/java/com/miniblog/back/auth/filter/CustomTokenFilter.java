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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomTokenFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final TokenService tokenService;
    private final SecurityProperties securityProperties;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (securityProperties.getPermitAllUrls().contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");

        tokenService.validateToken(authorizationHeader);

        Authentication authentication = tokenService.getAuthenticationFromToken(authorizationHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
