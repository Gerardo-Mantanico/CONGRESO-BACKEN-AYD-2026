package com.congreso.domain.dto.actividad;

import java.time.OffsetDateTime;

public record CreateActividadDto(
        String nombre,
        String descripcion,
        String tipo,
        OffsetDateTime horaInicio,
        OffsetDateTime horaFin,
        Integer capacidadMaxima,
        Long congresoId,
        Long convocatoriaId,
        String archivoUrl
) {}
