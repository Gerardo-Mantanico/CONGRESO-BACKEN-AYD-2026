package com.congreso.domain.service;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.InstitucionRepository;
import com.congreso.persistence.entity.InstitucionEntity;
import com.congreso.persistence.mapper.InstitucionMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class InstitucionService {
    private final InstitucionRepository institucionRepository;
    private final InstitucionMapper institucionMapper;

    public InstitucionResponseDto  create(CreateDtoInstitucion dto) {
        InstitucionEntity institucion = institucionMapper.dtoToEntity(dto);
        // Seguridad: no persistir valor en fotosUrl por ahora
        institucion.setFotosUrl(null);
        InstitucionEntity saved = institucionRepository.save(institucion);
        return institucionMapper.entityToDto(saved);
    }

    public InstitucionResponseDto update(Long id, CreateDtoInstitucion dto) {
        InstitucionEntity institucion = institucionRepository.findById(id)
                .orElseThrow(() -> new GeneralException("institucion-not-found", "Institución no encontrada"));
        institucionMapper.updateEntityFromDto(dto, institucion);
        // Seguridad: no persistir valor en fotosUrl por ahora
        institucion.setFotosUrl(null);
        InstitucionEntity updated = institucionRepository.save(institucion);
        return institucionMapper.entityToDto(updated);
    }

    public void delete(Long id) {
        InstitucionEntity institucion = institucionRepository.findById(id)
                .orElseThrow(() -> new GeneralException("institucion-not-found", "Institución no encontrada"));
        institucionRepository.delete(institucion);
    }

    public List<InstitucionResponseDto> listInstitucion() {
        return institucionRepository.findAll().stream()
                .map(institucionMapper::entityToDto)
                .toList();
    }

    // Métodos no-operativos (temporal): no modifican fotosUrl y devuelven el DTO actual
    public InstitucionResponseDto addFoto(Long id, String fotoUrl) {
        InstitucionEntity institucion = institucionRepository.findById(id)
                .orElseThrow(() -> new GeneralException("institucion-not-found", "Institución no encontrada"));
        // Por el momento no modificamos la columna fotos_url
        return institucionMapper.entityToDto(institucion);
    }

    public InstitucionResponseDto removeFoto(Long id, String fotoUrl) {
        InstitucionEntity institucion = institucionRepository.findById(id)
                .orElseThrow(() -> new GeneralException("institucion-not-found", "Institución no encontrada"));
        // Por el momento no modificamos la columna fotos_url
        return institucionMapper.entityToDto(institucion);
    }
}
