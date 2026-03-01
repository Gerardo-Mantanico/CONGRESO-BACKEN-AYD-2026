
package com.congreso.domain.service;

import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoRepository;
import com.congreso.persistence.entity.CongresoEntity;
import com.congreso.persistence.mapper.CongresoMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class CongresoService {
    private final CongresoRepository congresoRepository;
    private final InscripcionCongresoRepository inscripcionRepository;
    private final CongresoMapper congresoMapper;

    public List<CongresoResponseDto> list() {
        return congresoRepository.findByActivoTrue().stream()
                .map(congresoMapper::entityToDto)
                .toList();
    }

    public CongresoResponseDto getById(Long id) {
        CongresoEntity entity = congresoRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new GeneralException("congreso-not-found", "Congreso no encontrado"));
        return congresoMapper.entityToDto(entity);
    }

    public CongresoResponseDto create(CreateDtoCongreso dto) {
        validateDates(dto.fechaInicio(), dto.fechaFin());
        validatePrecio(dto.precioInscripcion());
        CongresoEntity entity = congresoMapper.dtoToEntity(dto);
        CongresoEntity saved = congresoRepository.save(entity);
        return congresoMapper.entityToDto(saved);
    }

    public CongresoResponseDto update(Long id, CreateDtoCongreso dto) {
        CongresoEntity entity = congresoRepository.findById(id)
                .orElseThrow(() -> new GeneralException("congreso-not-found", "Congreso no encontrado"));

        boolean hasInscripciones = inscripcionRepository.existsByCongresoId(id);

        // Si hay inscritos, no permitir cambiar precioInscripcion ni fechaInicio
        if (hasInscripciones) {
            if (dto.precioInscripcion() != null && dto.precioInscripcion().compareTo(entity.getPrecioInscripcion()) != 0) {
                throw new GeneralException("precio-update-not-allowed", "No se puede cambiar el precio de inscripción porque hay inscritos");
            }
            if (dto.fechaInicio() != null && !dto.fechaInicio().equals(entity.getFechaInicio())) {
                throw new GeneralException("fecha-inicio-update-not-allowed", "No se puede cambiar la fecha de inicio porque hay inscritos");
            }

            // Permitir solo cambios estéticos: aplicamos solo algunos campos
            entity.setDescripcion(dto.descripcion());
            entity.setUbicacion(dto.ubicacion());
            if (dto.fotoUrl() != null) entity.setFotoUrl(dto.fotoUrl());
            entity.setUpdatedAt(OffsetDateTime.now());
            CongresoEntity updated = congresoRepository.save(entity);
            return congresoMapper.entityToDto(updated);
        }

        // No hay inscritos: permitir update completo
        if (dto.precioInscripcion() != null) validatePrecio(dto.precioInscripcion());
        if (dto.fechaInicio() != null && dto.fechaFin() != null) validateDates(dto.fechaInicio(), dto.fechaFin());

        congresoMapper.updateEntityFromDto(dto, entity);
        CongresoEntity saved = congresoRepository.save(entity);
        return congresoMapper.entityToDto(saved);
    }

    public void deleteLogical(Long id) {
        CongresoEntity entity = congresoRepository.findById(id)
                .orElseThrow(() -> new GeneralException("congreso-not-found", "Congreso no encontrado"));
        entity.setActivo(false);
        entity.setUpdatedAt(OffsetDateTime.now());
        congresoRepository.save(entity);
    }

    private void validatePrecio(BigDecimal precio) {
        if (precio == null) return;
        if (precio.compareTo(new BigDecimal("35.00")) < 0) {
            throw new IllegalArgumentException("El precio de inscripción debe ser al menos 35.00");
        }
    }

    private void validateDates(OffsetDateTime inicio, OffsetDateTime fin) {
        if (inicio == null || fin == null) return;
        if (!inicio.isBefore(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }
    }
}