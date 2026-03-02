package com.congreso.domain.dto.inscripcion;

import java.util.List;

public record CreateInscripcionDto(Long usuarioId, Long congresoId, List<String> tipos) {
}

