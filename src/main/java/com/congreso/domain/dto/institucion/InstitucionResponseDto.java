package com.congreso.domain.dto.institucion;

import java.time.OffsetDateTime;
import java.util.List;

public record InstitucionResponseDto(Long id, String nombre, String descripcion, String direccion, List<String> fotosUrl, Boolean activo, OffsetDateTime creadoAt, OffsetDateTime updatedAt) {}
