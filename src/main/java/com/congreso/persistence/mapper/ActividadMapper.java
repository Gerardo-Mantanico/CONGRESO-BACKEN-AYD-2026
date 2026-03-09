package com.congreso.persistence.mapper;

import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.enums.Estados;
import com.congreso.persistence.entity.ActividadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActividadMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "congresoId.id", source = "congresoId")
    @Mapping(target = "convocatoriaId.id", source = "convocatoriaId")
    @Mapping(target = "user.id", ignore = true)
    ActividadEntity toEntity(CreateActividadDto dto);




    @Mapping(target = "estadoId", source = "estado")
    ActividadDto toDto(ActividadEntity entity);

    List<ActividadDto> toDto(Iterable<ActividadEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "congresoId.id", source = "congresoId")
    @Mapping(target = "convocatoriaId.id", source = "convocatoriaId")
    @Mapping(target = "user.id", ignore = true)
    void updateFromDto(CreateActividadDto dto, @MappingTarget ActividadEntity entity);

    default Long map(Estados estado) {
        return estado == null ? null : estado.getId();
    }

    default Estados map(Long id) {
        return Estados.fromId(id);
    }
}
