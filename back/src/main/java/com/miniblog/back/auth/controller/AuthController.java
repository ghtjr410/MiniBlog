package com.miniblog.back.auth.controller;

import com.miniblog.back.auth.dto.request.RefreshRequestDTO;
import com.miniblog.back.auth.dto.response.AccessTokenResponseDTO;
import com.miniblog.back.auth.service.AuthService;
import com.miniblog.back.auth.service.TokenService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        authService.logout(authorizationHeader);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponseDTO> refresh(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody RefreshRequestDTO requestDTO,
            HttpServletResponse httpServletResponse
    ) {
        AccessTokenResponseDTO responseDTO = tokenService.refresh(authorizationHeader, requestDTO, httpServletResponse);
        return ResponseEntity.ok(responseDTO);
    }
}
