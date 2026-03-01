package com.congreso.domain.dto.inscripcion;

import java.time.OffsetDateTime;
import java.util.List;

public record InscripcionResponseDto(Long id, Long usuarioId, Long congresoId, OffsetDateTime fechaInscripcion, List<String> tipos, Long pagoId) {
}

