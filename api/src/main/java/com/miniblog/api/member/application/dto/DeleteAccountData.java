package com.miniblog.api.member.application.dto;

import com.miniblog.api.member.api.dto.request.DeleteAccountRequest;

public record DeleteAccountData(
        String deviceInfo,
        long memberId,
        String refreshToken
) {
    public static DeleteAccountData of(DeleteAccountRequest request, long memberId, String refreshToken) {
        return new DeleteAccountData(request.deviceInfo(), memberId, refreshToken);
    }
}
