package com.congreso.domain.dto.congreso;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record CongresoResponseDto(
        Long id,
        String titulo,
        String descripcion,
        OffsetDateTime fechaInicio,
        OffsetDateTime fechaFin,
        String ubicacion,
        BigDecimal precioInscripcion,
        BigDecimal comisionPorcentaje,
        String fotoUrl,
        Boolean activo,
        Long institucionId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}

