package com.congreso.persistence.mapper;

import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.persistence.entity.CongresoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CongresoMapper {
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "institucionId", ignore = true)
    CongresoEntity dtoToEntity(CreateDtoCongreso dto);

    CongresoResponseDto entityToDto(CongresoEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "institucionId", ignore = true)
    void updateEntityFromDto(CreateDtoCongreso dto, @MappingTarget CongresoEntity entity);
}

