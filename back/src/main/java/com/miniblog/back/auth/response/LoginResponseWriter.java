package com.miniblog.back.auth.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.back.auth.config.JwtProperties;
import com.miniblog.back.auth.dto.response.AccessTokenResponseDTO;
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

    public void writeLoginSuccessResponse(HttpServletResponse httpServletResponse, String accessToken) throws IOException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(AccessTokenResponseDTO.of(accessToken)));
    }

    public void writeLoginFailedResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
