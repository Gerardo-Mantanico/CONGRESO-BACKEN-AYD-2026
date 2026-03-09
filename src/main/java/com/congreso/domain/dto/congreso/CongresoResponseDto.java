package com.congreso.domain.dto.congreso;

import com.congreso.domain.dto.institucion.InstitucionResponseDto;

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
        InstitucionResponseDto institucionId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}

