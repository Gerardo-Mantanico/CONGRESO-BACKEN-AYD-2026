package com.congreso.domain.dto.convocatoria;

import java.time.OffsetDateTime;

public record CreateConvocatoriaDto(
    String nombre,
    String descripcion,
    OffsetDateTime fechaInicio,
    OffsetDateTime fechaFin,
    Long congresoId
) {}
