package com.housing.back.dto.request.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditCommentRequestDto {
    private Long commentId;
    private String content;
}
