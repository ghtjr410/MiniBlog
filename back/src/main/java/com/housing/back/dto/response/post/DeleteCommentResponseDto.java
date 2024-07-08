package com.housing.back.dto.response.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class DeleteCommentResponseDto extends ResponseDto {
    private String message;

    private DeleteCommentResponseDto(String message) {
        super();
        this.message = message;
    }

    public static ResponseEntity<DeleteCommentResponseDto> success(String message) {
        DeleteCommentResponseDto responseBody = new DeleteCommentResponseDto(message);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
