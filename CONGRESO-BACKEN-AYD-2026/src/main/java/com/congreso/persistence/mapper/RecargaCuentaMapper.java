package com.congreso.persistence.mapper;

import com.congreso.domain.dto.recarga.RecargaCuentaCreationDto;
import com.congreso.domain.dto.recarga.RecargaCuentaDto;
import com.congreso.persistence.entity.RecargaCuentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecargaCuentaMapper {

    RecargaCuentaDto toDto(RecargaCuentaEntity entity);
    List<RecargaCuentaDto> toDto(Iterable<RecargaCuentaEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RecargaCuentaEntity toEntity(RecargaCuentaCreationDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cuentaDigitalId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(RecargaCuentaEntity entity, @MappingTarget RecargaCuentaEntity target);
}

