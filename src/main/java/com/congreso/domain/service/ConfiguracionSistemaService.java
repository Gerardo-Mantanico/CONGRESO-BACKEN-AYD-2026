package com.congreso.domain.service;

import com.congreso.domain.dto.ConfiguracionSistema.updateDto;
import com.congreso.domain.repository.ConfiguracionSistemaRepository;
import com.congreso.persistence.entity.ConfiguracionSistemaEntity;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracionSistemaService {

    private final ConfiguracionSistemaRepository configuracionSistemaRepository;

    public ConfiguracionSistemaService(ConfiguracionSistemaRepository configuracionSistemaRepository) {
        this.configuracionSistemaRepository = configuracionSistemaRepository;
    }

    public ConfiguracionSistemaEntity update(Long id, updateDto dto) {
        ConfiguracionSistemaEntity configuracionSistema = configuracionSistemaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Configuración del sistema no encontrada"));
        configuracionSistema.setComision(dto.comision());
        return configuracionSistemaRepository.save(configuracionSistema);
    }

    public ConfiguracionSistemaEntity getConfiguracionSistema() {
        return configuracionSistemaRepository.findAll().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Configuración del sistema no encontrada"));
    }
}
