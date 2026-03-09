package com.congreso.domain.dto.actividad;

import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.enums.Estados;

import java.time.OffsetDateTime;

public record ActividadDto(
        Long id,
        String nombre,
        String descripcion,
        String tipo,
        OffsetDateTime horaInicio,
        OffsetDateTime horaFin,
        Integer capacidadMaxima,
        CongresoResponseDto congresoId,
        ConvocatoriaDto convocatoriaId,
        UserDto userId,
        String archivoUrl,
        Estados estadoId,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
