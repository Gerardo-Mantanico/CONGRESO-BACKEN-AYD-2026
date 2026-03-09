package com.congreso.persistence.mapper;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.enums.Estados;
import com.congreso.persistence.entity.SalonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalonMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "estado", target = "estadoId", qualifiedByName = "estadoIdFromLabel")
    SalonEntity toEntity(CreateSalonDto dto);

    @Mapping(source = "estadoId", target = "estadoId")
    @Mapping(source = "estadoId", target = "estado", qualifiedByName = "estadoLabel")
    SalonDto toDto(SalonEntity entity);

    List<SalonDto> toDto(Iterable<SalonEntity> entities);

    @Named("estadoLabel")
    default String estadoLabel(Long estadoId) {
        Estados es = Estados.fromId(estadoId);
        return es == null ? null : es.getLabel();
    }

    @Named("estadoIdFromLabel")
    default Long estadoIdFromLabel(String label) {
        Estados es = Estados.fromLabel(label);
        return es == null ? null : es.getId();
    }
}
