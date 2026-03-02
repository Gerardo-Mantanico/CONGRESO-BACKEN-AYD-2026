package com.congreso.domain.service;

import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoRepository;
import com.congreso.persistence.entity.CongresoEntity;
import com.congreso.persistence.mapper.CongresoMapper;
import com.congreso.test.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CongresoServiceTest {

    @Mock
    private CongresoRepository congresoRepository;

    @Mock
    private InscripcionCongresoRepository inscripcionRepository;

    @Mock
    private CongresoMapper congresoMapper;

    @InjectMocks
    private CongresoService congresoService;

    private CreateDtoCongreso validDto;
    private CongresoEntity entity;

    @BeforeEach
    void setUp() {
        validDto = TestFixtures.createValidCongresoDto();
        entity = TestFixtures.createCongresoEntity();
    }

    @Test
    void create_shouldValidateDates_andPrecio() {
        // fecha inicio posterior a fin -> debe lanzar IllegalArgumentException
        CreateDtoCongreso badDates = new CreateDtoCongreso(
                "T",
                "D",
                OffsetDateTime.of(2026,3,15,0,0,0,0, ZoneOffset.UTC),
                OffsetDateTime.of(2026,3,14,0,0,0,0, ZoneOffset.UTC),
                "U",
                new BigDecimal("50.00"),
                null,
                null,
                1L
        );

        assertThrows(IllegalArgumentException.class, () -> congresoService.create(badDates));

        // precio menor a 35 -> debe lanzar IllegalArgumentException
        CreateDtoCongreso badPrecio = new CreateDtoCongreso(
                "T",
                "D",
                OffsetDateTime.of(2026,3,10,0,0,0,0, ZoneOffset.UTC),
                OffsetDateTime.of(2026,3,11,0,0,0,0, ZoneOffset.UTC),
                "U",
                new BigDecimal("10.00"),
                null,
                null,
                1L
        );

        assertThrows(IllegalArgumentException.class, () -> congresoService.create(badPrecio));
    }

    @Test
    void create_happyPath_returnsDto() {
        when(congresoMapper.dtoToEntity(validDto)).thenReturn(entity);
        when(congresoRepository.save(entity)).thenReturn(entity);
        CongresoResponseDto dto = new CongresoResponseDto(entity.getId(), entity.getTitulo(), entity.getDescripcion(), entity.getFechaInicio(), entity.getFechaFin(), null, entity.getPrecioInscripcion(), entity.getComisionPorcentaje(), null, entity.getActivo(), null, null, null);
        when(congresoMapper.entityToDto(entity)).thenReturn(dto);

        CongresoResponseDto res = congresoService.create(validDto);

        assertNotNull(res);
        assertEquals(entity.getId(), res.id());
        verify(congresoRepository).save(entity);
    }

    @Test
    void getById_whenNotFound_throws() {
        when(congresoRepository.findByIdAndActivoTrue(5L)).thenReturn(Optional.empty());
        assertThrows(GeneralException.class, () -> congresoService.getById(5L));
    }

    @Test
    void deleteLogical_marksInactive() {
        when(congresoRepository.findById(1L)).thenReturn(Optional.of(entity));
        congresoService.deleteLogical(1L);
        assertFalse(entity.getActivo());
        verify(congresoRepository).save(entity);
    }
}

