package com.congreso.persistence.mapper;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.persistence.entity.InstitucionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface InstitucionMapper {
    @Mapping(target = "activo", constant = "true")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    // Ignorar fotosUrl al crear desde DTO (no tomamos valores enviados por el cliente)
    @Mapping(target = "fotosUrl", ignore = true)
    InstitucionEntity dtoToEntity(CreateDtoInstitucion dto);

    // Convertir el String JSON de la entidad a List<String> en el DTO
    // MapStruct mapeará List<String> automáticamente
    InstitucionResponseDto entityToDto(InstitucionEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", expression = "java(java.time.OffsetDateTime.now())")
    // Ignorar fotosUrl en actualizaciones desde DTO
    @Mapping(target = "fotosUrl", ignore = true)
    void updateEntityFromDto(CreateDtoInstitucion dto, @MappingTarget InstitucionEntity entity);
}
