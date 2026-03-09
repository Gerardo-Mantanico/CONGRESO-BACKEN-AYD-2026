package com.congreso.domain.service;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.enums.Estados;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.SalonRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.persistence.entity.SalonEntity;
import com.congreso.persistence.mapper.SalonMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class SalonService {
    private final SalonRepository salonRepository;
    private final CongresoRepository congresoRepository;
    private final SalonMapper salonMapper;

    @Transactional
    public SalonDto create(CreateSalonDto dto) {
        // validar nombre unico dentro del congreso
        if (salonRepository.existsByNombreAndCongresoId(dto.getNombre(), dto.getCongresoId())) {
            throw new DomainException(HttpStatus.CONFLICT, "Ya existe un salón con ese nombre en el congreso");
        }

        // usar mapper para convertir dto -> entity
        SalonEntity entity = salonMapper.toEntity(dto);
        // asignar el estado por defecto
        entity.setEstadoId(Estados.ACTIVO.getId());
        SalonEntity saved = salonRepository.save(entity);
        // convertir con mapper entity -> dto
        return salonMapper.toDto(saved);
    }

    public List<SalonDto> listByCongreso(Long congresoId) {
        return salonMapper.toDto(salonRepository.findByCongresoIdAndActivoTrue(congresoId));
    }

    public List<SalonDto> listSalones() {
        return salonMapper.toDto(salonRepository.findAll());
    }
}
