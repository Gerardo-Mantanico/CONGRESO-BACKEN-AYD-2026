package com.congreso.domain.service;

import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.ActividadRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.ConvocatoriaRepository;
import com.congreso.domain.repository.UserRepository;
import com.congreso.persistence.entity.ActividadEntity;
import com.congreso.persistence.mapper.ActividadMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ActividadService {
    private final ActividadRepository actividadRepository;
    private final CongresoRepository congresoRepository;
    private final ConvocatoriaRepository convocatoriaRepository;
    private final UserRepository userRepository;
    private final ActividadMapper actividadMapper;
    private final UserUtilsService userUtilsService;

    @Transactional
    public ActividadDto create(CreateActividadDto dto) {
        // validar existencias
        var congreso = congresoRepository.findById(dto.congresoId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));
        var convocatoria = convocatoriaRepository.findById(dto.convocatoriaId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Convocatoria no encontrada"));

        // Validar horas
        if (dto.horaInicio() == null || dto.horaFin() == null) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "horaInicio y horaFin son obligatorias");
        }
        if (!dto.horaInicio().isBefore(dto.horaFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "horaInicio debe ser anterior a horaFin");
        }

        // Validar que la actividad esté dentro del rango del congreso
        if (dto.horaInicio().isBefore(congreso.getFechaInicio()) || dto.horaFin().isAfter(congreso.getFechaFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "La actividad debe estar dentro del rango de fechas del congreso");
        }

        ActividadEntity entity = actividadMapper.toEntity(dto);
        entity.setCongresoId(congreso);
        entity.setConvocatoriaId(convocatoria);
        entity.setUser(userUtilsService.getCurrent());

        if (dto.tipo().equalsIgnoreCase("PONENCIA")){
            entity.setCapacidadMaxima(null);
        } else if (dto.tipo().equalsIgnoreCase("TALLER")) {
            if (dto.capacidadMaxima() == null || dto.capacidadMaxima() <= 0) {
                throw new DomainException(HttpStatus.BAD_REQUEST, "capacidadMaxima debe ser mayor que 0 para TALLER");
            }
            entity.setCapacidadMaxima(dto.capacidadMaxima());
        }

        ActividadEntity saved = actividadRepository.save(entity);
        return actividadMapper.toDto(saved);
    }

    public ActividadDto getById(Long id) {
        ActividadEntity e = actividadRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        return actividadMapper.toDto(e);
    }

    @Transactional
    public ActividadDto update(Long id, CreateActividadDto dto) {
        ActividadEntity e = actividadRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        var congreso = congresoRepository.findById(dto.congresoId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));
        var convocatoria = convocatoriaRepository.findById(dto.convocatoriaId()).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Convocatoria no encontrada"));

        // Validar horas
        if (dto.horaInicio() == null || dto.horaFin() == null) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "horaInicio y horaFin son obligatorias");
        }
        if (!dto.horaInicio().isBefore(dto.horaFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "horaInicio debe ser anterior a horaFin");
        }

        // Validar que la actividad esté dentro del rango del congreso
        if (dto.horaInicio().isBefore(congreso.getFechaInicio()) || dto.horaFin().isAfter(congreso.getFechaFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "La actividad debe estar dentro del rango de fechas del congreso");
        }

        actividadMapper.updateFromDto(dto, e);
        e.setCongresoId(congreso);
        e.setConvocatoriaId(convocatoria);
        e.setUser(userUtilsService.getCurrent());

        if (dto.tipo().equalsIgnoreCase("PONENCIA")){
            e.setCapacidadMaxima(null);
        } else if (dto.tipo().equalsIgnoreCase("TALLER")) {
            if (dto.capacidadMaxima() == null || dto.capacidadMaxima() <= 0) {
                throw new DomainException(HttpStatus.BAD_REQUEST, "capacidadMaxima debe ser mayor que 0 para TALLER");
            }
            e.setCapacidadMaxima(dto.capacidadMaxima());
        }

        ActividadEntity saved = actividadRepository.save(e);
        return actividadMapper.toDto(saved);
    }

    @Transactional
    public void delete(Long id) {
        ActividadEntity e = actividadRepository.findById(id).orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        e.setActivo(false);
        actividadRepository.save(e);
    }

    public Page<ActividadDto> listAll(Pageable pageable) {
        var page = actividadRepository.findByActivoTrueAndUser_Id(pageable, userUtilsService.getCurrent().getId());
        return page.map(actividadMapper::toDto);
    }

    public Page<ActividadDto> listByCongreso(Long congresoId, Pageable pageable) {
        var page = actividadRepository.findByCongresoIdIdAndActivoTrue(congresoId, pageable);
        return page.map(actividadMapper::toDto);
    }

    public Page<ActividadDto> listByConvocatoria(Long convocatoriaId, Pageable pageable) {
        var page = actividadRepository.findByConvocatoriaIdIdAndActivoTrue(convocatoriaId, pageable);
        return page.map(actividadMapper::toDto);
    }
}
