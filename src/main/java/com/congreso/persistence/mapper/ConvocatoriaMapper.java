package com.congreso.persistence.mapper;

import com.congreso.domain.dto.convocatoria.CreateConvocatoriaDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.persistence.entity.ConvocatoriaEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConvocatoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "congresoId.id", source = "congresoId")
    ConvocatoriaEntity toEntity(CreateConvocatoriaDto dto);

    // mapeo explícito: tomar el label del enum para el DTO
    @Mapping(target = "estado", expression = "java(entity.getEstado() == null ? null : entity.getEstado().getLabel())")
    ConvocatoriaDto toDto(ConvocatoriaEntity entity);
    List<ConvocatoriaDto> toDto(Iterable<ConvocatoriaEntity> entities);

    @InheritConfiguration(name = "toEntity")
    void updateFromDto(CreateConvocatoriaDto dto, @MappingTarget ConvocatoriaEntity entity);

}