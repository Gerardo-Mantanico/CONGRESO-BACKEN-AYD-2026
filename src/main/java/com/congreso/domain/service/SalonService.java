package com.congreso.domain.service;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.SalonRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.persistence.entity.SalonEntity;
import com.congreso.persistence.entity.CongresoEntity;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SalonService {
    private final SalonRepository salonRepository;
    private final CongresoRepository congresoRepository;

    @Transactional
    public SalonDto create(CreateSalonDto dto) {
        // validar congreso existe
        CongresoEntity congreso = congresoRepository.findById(dto.getCongresoId())
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));

        // validar nombre unico dentro del congreso
        if (salonRepository.existsByNombreAndCongresoId(dto.getNombre(), dto.getCongresoId())) {
            throw new DomainException(HttpStatus.CONFLICT, "Ya existe un salón con ese nombre en el congreso");
        }

        SalonEntity entity = new SalonEntity();
        entity.setNombre(dto.getNombre());
        entity.setUbicacion(dto.getUbicacion());
        entity.setCapacidad(dto.getCapacidad());
        entity.setRecursos(dto.getRecursos());
        entity.setCongresoId(dto.getCongresoId());
        SalonEntity saved = salonRepository.save(entity);

        SalonDto out = new SalonDto();
        out.setId(saved.getId());
        out.setNombre(saved.getNombre());
        out.setUbicacion(saved.getUbicacion());
        out.setCapacidad(saved.getCapacidad());
        out.setRecursos(saved.getRecursos());
        out.setCongresoId(saved.getCongresoId());
        return out;
    }

    public List<SalonDto> listByCongreso(Long congresoId) {
        return salonRepository.findByCongresoIdAndActivoTrue(congresoId).stream().map(s -> {
            SalonDto dto = new SalonDto();
            dto.setId(s.getId()); dto.setNombre(s.getNombre()); dto.setUbicacion(s.getUbicacion()); dto.setCapacidad(s.getCapacidad()); dto.setRecursos(s.getRecursos()); dto.setCongresoId(s.getCongresoId());
            return dto;
        }).collect(Collectors.toList());
    }
}

