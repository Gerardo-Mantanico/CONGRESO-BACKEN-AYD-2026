package com.congreso.persistence.mapper;

import com.congreso.domain.dto.user.UserCreationDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "dpi", source = "dpi")
    @Mapping(target = "fotoUrl", source = "fotoUrl")
    UserDto toDto(UserEntity userEntity);
    List<UserDto> toDto(Iterable<UserEntity> movieEntityList);

    @Mapping(target = "use2fa", defaultValue = "false")
    UserEntity toEntity(UserDto userDto);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    UserEntity toEntity(UserCreationDto userDto);
}
