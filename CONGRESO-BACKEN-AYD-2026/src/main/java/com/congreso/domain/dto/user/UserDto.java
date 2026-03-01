package com.congreso.domain.dto.user;

import com.congreso.domain.dto.role.RoleDto;

import java.time.Instant;

public record UserDto(
        Long id,
        String firstname,
        String lastname,
        String email,
        String phoneNumber,
        String dpi,
        Instant createdAt,
        Instant updatedAt,
        Boolean active,
        RoleDto role,
        Boolean use2fa,
        String fotoUrl
) {
}
