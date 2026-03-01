package com.congreso.persistence.mapper;

import com.congreso.persistence.entity.ConfiguracionSistemaEntity;
import com.congreso.domain.dto.ConfiguracionSistema.updateDto;

public class ConfiguracionSistemaMapper {
    public static updateDto toDto(ConfiguracionSistemaEntity entity) {
        if (entity == null) return null;
        return new updateDto(entity.getComision());
    }

    public static ConfiguracionSistemaEntity toEntity(updateDto dto) {
        if (dto == null) return null;
        ConfiguracionSistemaEntity entity = new ConfiguracionSistemaEntity();
        entity.setComision(dto.comision());
        return entity;
    }
}
