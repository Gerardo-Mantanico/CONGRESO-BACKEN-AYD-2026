package com.congreso.domain.dto.convocatoria;

import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.persistence.entity.CongresoEntity;

import java.time.OffsetDateTime;

public record ConvocatoriaDto(
        Long id,
        String nombre,
        String descripcion,
        OffsetDateTime fechaInicio,
        OffsetDateTime fechaFin,
        CongresoResponseDto congresoId,
        String estado,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
