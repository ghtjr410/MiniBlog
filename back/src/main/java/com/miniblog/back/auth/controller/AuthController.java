package com.miniblog.back.auth.controller;

import com.miniblog.back.auth.dto.request.RefreshRequestDTO;
import com.miniblog.back.auth.dto.response.RefreshResponseDTO;
import com.miniblog.back.auth.model.BlacklistToken;
import com.miniblog.back.auth.repository.BlacklistTokenRepository;
import com.miniblog.back.auth.service.AuthService;
import com.miniblog.back.auth.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final BlacklistTokenRepository blacklistTokenRepository;

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorizationHeader) {
        authService.logout(authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponseDTO> refresh(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody RefreshRequestDTO requestDTO,
            HttpServletResponse httpServletResponse
    ) {
        RefreshResponseDTO responseDTO = tokenService.refresh(authorizationHeader, requestDTO, httpServletResponse);
        return ResponseEntity.ok(responseDTO);
    }
}
