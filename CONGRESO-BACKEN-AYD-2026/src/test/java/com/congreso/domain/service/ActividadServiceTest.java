package com.congreso.domain.service;

import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.ActividadRepository;
import com.congreso.domain.repository.SalonRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.persistence.entity.SalonEntity;
import com.congreso.persistence.entity.CongresoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActividadServiceTest {

    @Mock
    private ActividadRepository actividadRepository;

    @Mock
    private SalonRepository salonRepository;

    @Mock
    private CongresoRepository congresoRepository;

    @InjectMocks
    private ActividadService actividadService;

    private SalonEntity salon;
    private CongresoEntity congreso;

    @BeforeEach
    void setUp() {
        salon = new SalonEntity();
        salon.setId(1L);
        salon.setCapacidad(50);
        salon.setCongresoId(100L);

        congreso = new CongresoEntity();
        congreso.setId(100L);
        congreso.setFechaInicio(OffsetDateTime.of(2026,3,1,8,0,0,0, ZoneOffset.UTC));
        congreso.setFechaFin(OffsetDateTime.of(2026,3,3,18,0,0,0, ZoneOffset.UTC));
    }

    @Test
    void create_shouldFail_whenOverlap() {
        CreateActividadDto dto = new CreateActividadDto();
        dto.setNombre("Charla nueva");
        dto.setTipo("PONENCIA");
        dto.setHoraInicio(OffsetDateTime.of(2026,3,1,10,0,0,0, ZoneOffset.UTC));
        dto.setHoraFin(OffsetDateTime.of(2026,3,1,11,0,0,0, ZoneOffset.UTC));
        dto.setSalonId(1L);
        dto.setCongresoId(100L);

        when(salonRepository.findById(1L)).thenReturn(Optional.of(salon));
        when(congresoRepository.findById(100L)).thenReturn(Optional.of(congreso));
        // Simular que existe actividad de 9:30 - 10:30
        when(actividadRepository.existsOverlap(eq(1L), any(), any())).thenReturn(true);

        DomainException ex = assertThrows(DomainException.class, () -> actividadService.create(dto));
        assertEquals(409, ex.getStatus());
        verify(actividadRepository, never()).save(any());
    }
}
