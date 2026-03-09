package com.congreso.domain.service;

import com.congreso.domain.dto.convocatoria.CreateConvocatoriaDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.domain.enums.Estados;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.ConvocatoriaRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.utils.DateRangeValidator;
import com.congreso.persistence.entity.ConvocatoriaEntity;
import com.congreso.persistence.mapper.ConvocatoriaMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ConvocatoriaService {
    private final ConvocatoriaRepository convocatoriaRepository;
    private final CongresoRepository congresoRepository;
    private final ConvocatoriaMapper convocatoriaMapper;
    private final DateRangeValidator dateRangeValidator;

    @Transactional
    public ConvocatoriaDto create(CreateConvocatoriaDto dto) {
        // validar congreso existe
        var congreso = congresoRepository.findById(dto.congresoId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));

        // Validar fechas internas de la convocatoria
        dateRangeValidator.validateStartBeforeEnd(dto.fechaInicio(), dto.fechaFin(), "Fechas inválidas: fechaInicio debe ser anterior a fechaFin");

        // Validar que la convocatoria no se realice después del inicio del congreso
        if (dto.fechaInicio().isAfter(congreso.getFechaInicio()) || dto.fechaFin().isAfter(congreso.getFechaInicio())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "La convocatoria debe estar antes de la fecha de inicio del congreso");
        }

        ConvocatoriaEntity entity = convocatoriaMapper.toEntity(dto);
        entity.setCongresoId(congreso);
        ConvocatoriaEntity saved = convocatoriaRepository.save(entity);
        return convocatoriaMapper.toDto(saved);
    }

    public ConvocatoriaDto getById(Long id) {
        ConvocatoriaEntity e = convocatoriaRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Convocatoria no encontrada"));
        return convocatoriaMapper.toDto(e);
    }

    @Transactional
    public ConvocatoriaDto update(Long id, CreateConvocatoriaDto dto) {
        ConvocatoriaEntity e = convocatoriaRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Convocatoria no encontrada"));
        // validar fechas
        dateRangeValidator.validateStartBeforeEnd(dto.fechaInicio(), dto.fechaFin(), "Fechas inválidas: fechaInicio debe ser anterior a fechaFin");
        // asegurar que la convocatoria siga antes del inicio del congreso
        var congreso = congresoRepository.findById(e.getCongresoId().getId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));
        if (dto.fechaInicio().isAfter(congreso.getFechaInicio()) || dto.fechaFin().isAfter(congreso.getFechaInicio())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "La convocatoria debe estar antes de la fecha de inicio del congreso");
        }
        convocatoriaMapper.updateFromDto(dto, e);
        ConvocatoriaEntity saved = convocatoriaRepository.save(e);
        return convocatoriaMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        ConvocatoriaEntity e = convocatoriaRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Convocatoria no encontrada"));
        e.setActivo(false);
        convocatoriaRepository.save(e);
    }

    public Page<ConvocatoriaDto> listByCongreso(Long congresoId, Pageable pageable) {
        var page = convocatoriaRepository.findByCongresoIdIdAndActivoTrue(congresoId, pageable);
        return page.map(convocatoriaMapper::toDto);
    }

    public Page<ConvocatoriaDto> listAll(Pageable pageable) {
        var page = convocatoriaRepository.findByActivoTrue(pageable);
        return page.map(convocatoriaMapper::toDto);
    }
}
