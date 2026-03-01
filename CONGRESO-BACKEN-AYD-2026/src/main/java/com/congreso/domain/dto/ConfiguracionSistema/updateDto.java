package com.congreso.domain.dto.ConfiguracionSistema;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record updateDto(

        @NotNull
        @Positive
        BigDecimal comision
) {
}
