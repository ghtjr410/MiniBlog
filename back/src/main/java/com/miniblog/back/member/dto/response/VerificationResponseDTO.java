package com.miniblog.back.member.dto.response;

public record VerificationResponseDTO(
        boolean verified
) {
    public static VerificationResponseDTO of(boolean verified) {
        return new VerificationResponseDTO(
                verified
        );
    }
}
