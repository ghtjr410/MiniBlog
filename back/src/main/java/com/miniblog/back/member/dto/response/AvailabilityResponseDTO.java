package com.miniblog.back.member.dto.response;

public record AvailabilityResponseDTO(
        boolean available
) {
    public static AvailabilityResponseDTO of(boolean available) {
        return new AvailabilityResponseDTO(
                available
        );
    }
}
