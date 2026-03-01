package com.congreso.domain.dto.recarga;

import java.math.BigDecimal;
import java.time.Instant;

public record RecargaCuentaDto(
        Long id,
        Long cuentaDigitalId,
        BigDecimal monto,
        String moneda,
        String referencia,
        String estado,
        String medioPago,
        Long operadorId,
        Instant fechaRecarga,
        Instant createdAt,
        Instant updatedAt
) {
}

