package com.congreso.domain.dto.user;

public record UserSimpleDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber
) {
}
