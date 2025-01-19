package com.miniblog.back.viewcount.controller;

import com.miniblog.back.viewcount.dto.request.IncrementViewCountRequestDTO;
import com.miniblog.back.viewcount.service.ViewCountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/view-count")
@RequiredArgsConstructor
public class ViewCountController {
    private final ViewCountService viewCountService;

    @PostMapping("/increment")
    public ResponseEntity<?> incrementViewCount(
            @Valid @RequestBody IncrementViewCountRequestDTO requestDTO
    ) {
        viewCountService.incrementViewCount(requestDTO);
        return ResponseEntity.noContent().build();
    }
}
