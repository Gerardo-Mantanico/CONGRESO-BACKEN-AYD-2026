package com.congreso.persistence.mapper;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.persistence.entity.InstitucionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InstitucionMapper {
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    InstitucionEntity dtoToEntity(CreateDtoInstitucion dto);

    InstitucionResponseDto entityToDto(InstitucionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    void updateEntityFromDto(CreateDtoInstitucion dto, @MappingTarget InstitucionEntity entity);
}
