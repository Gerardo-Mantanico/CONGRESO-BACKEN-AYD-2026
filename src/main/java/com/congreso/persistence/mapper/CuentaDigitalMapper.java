package com.congreso.persistence.mapper;

import com.congreso.domain.dto.cuenta.CuentaDigitalCreationDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalUpdateDto;
import com.congreso.persistence.entity.CuentaDigitalEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaDigitalMapper {

    CuentaDigitalDto toDto(CuentaDigitalEntity entity);
    List<CuentaDigitalDto> toDto(Iterable<CuentaDigitalEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CuentaDigitalEntity toEntity(CuentaDigitalCreationDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuarioId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromDto(CuentaDigitalUpdateDto dto, @MappingTarget CuentaDigitalEntity entity);

    // MapStruct helper methods to convert between OffsetDateTime and Instant
    default Instant map(OffsetDateTime odt) {
        return odt == null ? null : odt.toInstant();
    }

    default OffsetDateTime map(Instant instant) {
        return instant == null ? null : OffsetDateTime.ofInstant(instant, ZoneOffset.UTC);
    }
}
