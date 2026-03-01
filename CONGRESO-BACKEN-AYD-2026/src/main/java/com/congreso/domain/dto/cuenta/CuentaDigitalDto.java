package com.congreso.domain.dto.cuenta;

import java.math.BigDecimal;
import java.time.Instant;

public record CuentaDigitalDto(
        Long id,
        Long usuarioId,
        BigDecimal saldo,
        String moneda,
        Boolean activa,
        Instant createdAt,
        Instant updatedAt
) {
}

