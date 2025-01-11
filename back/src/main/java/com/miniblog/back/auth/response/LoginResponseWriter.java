package com.miniblog.back.auth.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.back.auth.config.JwtProperties;
import com.miniblog.back.auth.dto.response.LoginResponseDTO;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginResponseWriter {

    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    public void addCookie(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge((int) (jwtProperties.getRefreshTokenExpiration() / 1000));
        httpServletResponse.addCookie(refreshTokenCookie);
    }

    public void writeJsonResponse(HttpServletResponse httpServletResponse, String accessToken) throws IOException {
        LoginResponseDTO loginResponse = new LoginResponseDTO(accessToken);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(loginResponse));
    }
}
