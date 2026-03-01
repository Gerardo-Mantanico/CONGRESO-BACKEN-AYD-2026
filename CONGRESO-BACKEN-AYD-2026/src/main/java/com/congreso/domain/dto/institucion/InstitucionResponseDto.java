package com.congreso.domain.dto.institucion;

import java.time.OffsetDateTime;

public record InstitucionResponseDto(Long id, String nombre, String descripcion, String direccion, Boolean activo, OffsetDateTime creadoAt, OffsetDateTime updatedAt) {}
