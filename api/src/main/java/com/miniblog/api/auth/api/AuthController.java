package com.miniblog.api.auth.api;

import com.miniblog.api.auth.api.dto.request.LoginRequest;
import com.miniblog.api.auth.api.dto.request.LogoutRequest;
import com.miniblog.api.auth.api.dto.request.TokenRenewalRequest;
import com.miniblog.api.auth.application.business.LoginService;
import com.miniblog.api.auth.application.business.LogoutService;
import com.miniblog.api.auth.application.business.TokenRenewalService;
import com.miniblog.api.auth.application.dto.AccessTokenInfoData;
import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.auth.application.dto.LoginData;
import com.miniblog.api.auth.application.dto.LogoutData;
import com.miniblog.api.auth.application.util.TokenType;
import com.miniblog.api.common.api.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService authService;
    private final LogoutService logoutService;
    private final TokenRenewalService tokenRenewalService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AccessTokenInfoData>> login(
            @RequestBody LoginRequest request,
            HttpServletResponse httpResponse
    ) {
        LoginData data = LoginData.of(request);

        JwtTokensData tokens = authService.login(data);

        setRefreshTokenCookie(httpResponse, tokens.refreshTokenInfo().refreshToken());
        return ResponseEntity.status(CREATED).body(ApiResponse.success(tokens.accessTokenInfo()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            @RequestBody LogoutRequest request
    ) {
        logoutService.logout(refreshToken, request.deviceInfo());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/renew")
    public ResponseEntity<ApiResponse<AccessTokenInfoData>> renew(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            @RequestBody TokenRenewalRequest request,
            HttpServletResponse httpResponse
    ) {
        JwtTokensData tokens = tokenRenewalService.renew(refreshToken, request.deviceInfo());

        setRefreshTokenCookie(httpResponse, tokens.refreshTokenInfo().refreshToken());
        return ResponseEntity.status(CREATED).body(ApiResponse.success(tokens.accessTokenInfo()));
    }

    private void setRefreshTokenCookie(HttpServletResponse httpResponse, String refreshToken) {
        Cookie cookie = new Cookie(TokenType.REFRESH_TOKEN.getKey(), refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        httpResponse.addCookie(cookie);
    }
}
