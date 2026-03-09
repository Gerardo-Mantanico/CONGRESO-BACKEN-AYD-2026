package com.congreso.domain.dto.institucion;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FotoDto(
        @NotNull
        @Size(min = 1)
        String url
) {}

