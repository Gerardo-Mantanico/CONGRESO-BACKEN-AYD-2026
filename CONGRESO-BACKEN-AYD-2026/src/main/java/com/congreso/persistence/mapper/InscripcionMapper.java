package com.congreso.persistence.mapper;

import com.congreso.domain.dto.inscripcion.InscripcionResponseDto;
import com.congreso.persistence.entity.InscripcionCongresoEntity;
import com.congreso.persistence.entity.InscripcionCongresoTipoEntity;

import java.util.List;
import java.util.stream.Collectors;

public class InscripcionMapper {
    public static InscripcionResponseDto entityToDto(InscripcionCongresoEntity e, List<InscripcionCongresoTipoEntity> tipos, Long pagoId) {
        List<String> tiposStr = tipos == null ? java.util.Collections.emptyList() : tipos.stream().map(InscripcionCongresoTipoEntity::getTipoParticipacion).collect(Collectors.toList());
        return new InscripcionResponseDto(e.getId(), e.getUsuarioId(), e.getCongresoId(), e.getFechaInscripcion(), tiposStr, pagoId);
    }
}

