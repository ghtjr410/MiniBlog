package com.miniblog.back.auth.repository;

import com.miniblog.back.auth.dto.internal.RefreshTokenDTO;

import java.util.List;

public interface TokenRepositoryCustom {
    List<RefreshTokenDTO> findTokensByMemberId(Long memberId);
}
