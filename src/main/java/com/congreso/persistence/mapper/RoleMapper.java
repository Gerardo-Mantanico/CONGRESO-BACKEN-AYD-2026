package com.congreso.persistence.mapper;

import com.congreso.domain.dto.role.RoleDto;
import com.congreso.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(RoleEntity roleEntity);
    List<RoleDto> toDto(List<RoleEntity> roleEntity);
}
