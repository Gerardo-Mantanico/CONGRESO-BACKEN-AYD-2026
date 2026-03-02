package com.congreso.domain.dto.login;

import com.congreso.domain.dto.user.UserDto;

public record LoginResponseDto(
        Boolean use2fa,
        UserDto user
) {
}
