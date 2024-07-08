package com.housing.back.dto.response.post;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.housing.back.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class EditCommentResponseDto extends ResponseDto {
    private String content;

    private EditCommentResponseDto(String content) {
        super();
        this.content = content;
    }

    public static ResponseEntity<EditCommentResponseDto> success(String content) {
        EditCommentResponseDto responseBody = new EditCommentResponseDto(content);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}