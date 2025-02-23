package com.miniblog.api.query.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthorDto {
    private Long authorId;
    private String nickname;

    @QueryProjection
    public AuthorDto(Long authorId, String nickname) {
        this.authorId = authorId;
        this.nickname = nickname;
    }
}
