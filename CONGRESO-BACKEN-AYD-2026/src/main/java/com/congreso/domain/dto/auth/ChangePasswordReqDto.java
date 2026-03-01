package com.congreso.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordReqDto(
        @NotBlank
        String code,

        @NotBlank
        @Email
        String email,

        @NotBlank
        String newPassword
) {
}
