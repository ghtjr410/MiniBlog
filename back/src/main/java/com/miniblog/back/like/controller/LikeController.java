package com.miniblog.back.like.controller;

import com.miniblog.back.like.service.LikeService;
import com.miniblog.back.like.dto.request.ToggleLikeRequestDTO;
import com.miniblog.back.like.dto.response.ToggleLikeResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/toggle")
    public ResponseEntity<ToggleLikeResponseDTO> toggleLike(
            @RequestHeader("Authorization") String authorizationHeader,
            @Valid @RequestBody ToggleLikeRequestDTO requestDTO
    ) {
        ToggleLikeResponseDTO responseDTO = likeService.toggleLike(authorizationHeader,requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
