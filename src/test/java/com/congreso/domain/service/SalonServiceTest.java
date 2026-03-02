package com.congreso.domain.service;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.SalonRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.persistence.entity.SalonEntity;
import com.congreso.persistence.entity.CongresoEntity;
import com.congreso.test.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SalonServiceTest {

    @Mock
    private SalonRepository salonRepository;

    @Mock
    private CongresoRepository congresoRepository;

    @InjectMocks
    private SalonService salonService;

    private CreateSalonDto dto;
    private SalonEntity entity;
    private CongresoEntity congreso;

    @BeforeEach
    void setUp() {
        dto = TestFixtures.createSalonDto();
        entity = TestFixtures.createSalonEntity();
        congreso = TestFixtures.createCongresoEntity();
    }

    @Test
    void create_whenCongresoNotFound_throwsNotFound() {
        when(congresoRepository.findById(dto.getCongresoId())).thenReturn(Optional.empty());
        DomainException ex = assertThrows(DomainException.class, () -> salonService.create(dto));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(salonRepository, never()).save(any());
    }

    @Test
    void create_whenNombreExists_throwsConflict() {
        when(congresoRepository.findById(dto.getCongresoId())).thenReturn(Optional.of(congreso));
        when(salonRepository.existsByNombreAndCongresoId(dto.getNombre(), dto.getCongresoId())).thenReturn(true);
        DomainException ex = assertThrows(DomainException.class, () -> salonService.create(dto));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(salonRepository, never()).save(any());
    }

    @Test
    void create_happyPath_returnsDto() {
        when(congresoRepository.findById(dto.getCongresoId())).thenReturn(Optional.of(congreso));
        when(salonRepository.existsByNombreAndCongresoId(dto.getNombre(), dto.getCongresoId())).thenReturn(false);
        when(salonRepository.save(any(SalonEntity.class))).thenReturn(entity);

        SalonDto res = salonService.create(dto);

        assertNotNull(res);
        assertEquals(entity.getId(), res.getId());
        assertEquals(entity.getNombre(), res.getNombre());
        verify(salonRepository).save(any(SalonEntity.class));
    }
}

