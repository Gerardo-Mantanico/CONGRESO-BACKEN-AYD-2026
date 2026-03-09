package com.congreso.domain.service;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.InstitucionRepository;
import com.congreso.persistence.entity.InstitucionEntity;
import com.congreso.persistence.mapper.InstitucionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InstitucionServiceTest {

    @Mock
    private InstitucionRepository institucionRepository;

    @Mock
    private InstitucionMapper institucionMapper;

    @InjectMocks
    private InstitucionService institucionService;

    private CreateDtoInstitucion dto;
    private InstitucionEntity entity;

    @BeforeEach
    void setUp() {
        dto = new CreateDtoInstitucion("Nombre", "Desc corta", "Direccion", List.of());
        entity = new InstitucionEntity();
        entity.setId(5L);
        entity.setNombre("Nombre");
        entity.setDireccion("Direccion");
    }

    @Test
    void create_happyPath() {
        when(institucionMapper.dtoToEntity(dto)).thenReturn(entity);
        when(institucionRepository.save(entity)).thenReturn(entity);
        InstitucionResponseDto out = new InstitucionResponseDto(entity.getId(), entity.getNombre(), "Desc corta", entity.getDireccion(), List.of(), true, java.time.OffsetDateTime.now(), java.time.OffsetDateTime.now());
        when(institucionMapper.entityToDto(entity)).thenReturn(out);

        InstitucionResponseDto res = institucionService.create(dto);

        assertNotNull(res);
        assertEquals(entity.getId(), res.id());
    }

    @Test
    void update_whenNotFound_throws() {
        when(institucionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(GeneralException.class, () -> institucionService.update(1L, dto));
    }

    @Test
    void delete_whenNotFound_throws() {
        when(institucionRepository.findById(9L)).thenReturn(Optional.empty());
        assertThrows(GeneralException.class, () -> institucionService.delete(9L));
    }

    @Test
    void list_returnsMapped() {
        when(institucionRepository.findAll()).thenReturn(List.of(entity));
        InstitucionResponseDto out = new InstitucionResponseDto(entity.getId(), entity.getNombre(), "Desc corta", entity.getDireccion(), List.of(), true, java.time.OffsetDateTime.now(), java.time.OffsetDateTime.now());
        when(institucionMapper.entityToDto(entity)).thenReturn(out);

        var res = institucionService.listInstitucion();
        assertEquals(1, res.size());
        assertEquals(entity.getId(), res.get(0).id());
    }
}
