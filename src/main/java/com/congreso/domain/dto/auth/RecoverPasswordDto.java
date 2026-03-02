package com.congreso.domain.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecoverPasswordDto(
        @NotBlank
        @Email
        String email
) { }
