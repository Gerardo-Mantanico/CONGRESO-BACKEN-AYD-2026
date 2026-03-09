package com.congreso.domain.service;

import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.domain.dto.user.UserDto;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.ActividadRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.ConvocatoriaRepository;
import com.congreso.domain.repository.UserRepository;
import com.congreso.domain.enums.Estados;
import com.congreso.persistence.entity.ActividadEntity;
import com.congreso.persistence.entity.CongresoEntity;
import com.congreso.persistence.entity.ConvocatoriaEntity;
import com.congreso.persistence.entity.UserEntity;
import com.congreso.persistence.mapper.ActividadMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActividadServiceTest {

    @Mock
    ActividadRepository actividadRepository;

    @Mock
    CongresoRepository congresoRepository;

    @Mock
    ConvocatoriaRepository convocatoriaRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ActividadMapper actividadMapper;

    @Mock
    UserUtilsService userUtilsService;

    @InjectMocks
    ActividadService actividadService;

    private CongresoEntity congreso;
    private ConvocatoriaEntity convocatoria;
    private UserEntity user;

    @BeforeEach
    void setUp() {
        congreso = new CongresoEntity();
        congreso.setId(1L);
        congreso.setFechaInicio(OffsetDateTime.now().plusDays(1));
        congreso.setFechaFin(OffsetDateTime.now().plusDays(3));

        convocatoria = new ConvocatoriaEntity();
        convocatoria.setId(1L);
        convocatoria.setCongresoId(congreso);

        user = new UserEntity();
        user.setId(1L);
    }

    @Test
    void create_activity_outside_congress_range_throws() {
        CreateActividadDto dto = new CreateActividadDto(
                "Actividad fuera",
                "desc",
                "PONENCIA",
                OffsetDateTime.now().minusDays(1), // antes del congreso
                OffsetDateTime.now().minusDays(1).plusHours(2),
                null,
                congreso.getId(),
                convocatoria.getId(),
                null
        );

        when(congresoRepository.findById(congreso.getId())).thenReturn(Optional.of(congreso));
        when(convocatoriaRepository.findById(convocatoria.getId())).thenReturn(Optional.of(convocatoria));

        DomainException ex = assertThrows(DomainException.class, () -> actividadService.create(dto));
        assertEquals(400, ex.getStatus());
    }

    @Test
    void create_taller_with_zero_capacity_throws() {
        CreateActividadDto dto = new CreateActividadDto(
                "Taller",
                "desc",
                "TALLER",
                congreso.getFechaInicio().plusHours(1),
                congreso.getFechaInicio().plusHours(2),
                0,
                congreso.getId(),
                convocatoria.getId(),
                null
        );

        when(congresoRepository.findById(congreso.getId())).thenReturn(Optional.of(congreso));
        when(convocatoriaRepository.findById(convocatoria.getId())).thenReturn(Optional.of(convocatoria));

        DomainException ex = assertThrows(DomainException.class, () -> actividadService.create(dto));
        assertEquals(400, ex.getStatus());
    }

    @Test
    void create_valid_actividad_returnsDto() {
        CreateActividadDto dto = new CreateActividadDto(
                "Valida",
                "desc",
                "PONENCIA",
                congreso.getFechaInicio().plusHours(1),
                congreso.getFechaInicio().plusHours(2),
                null,
                congreso.getId(),
                convocatoria.getId(),
                null
        );

        ActividadEntity entity = new ActividadEntity();
        entity.setId(10L);

        // construir los DTOs anidados requeridos por ActividadDto
        InstitucionResponseDto institucionDto = new InstitucionResponseDto(1L, "Inst", "desc", "dir", null, true, null, null);
        CongresoResponseDto congresoDto = new CongresoResponseDto(congreso.getId(), "T", "D", congreso.getFechaInicio(), congreso.getFechaFin(), "U", null, null, null, true, institucionDto, null, null);
        ConvocatoriaDto convocDto = new ConvocatoriaDto(convocatoria.getId(), "N", "D", congreso.getFechaInicio(), congreso.getFechaInicio().plusDays(1), congresoDto, "ACT", null, null);
        UserDto userDto = new UserDto(user.getId(), "f", "l", "e@e", "p", "dpi", null, null, true, null, false, null);

        when(congresoRepository.findById(congreso.getId())).thenReturn(Optional.of(congreso));
        when(convocatoriaRepository.findById(convocatoria.getId())).thenReturn(Optional.of(convocatoria));
        when(userUtilsService.getCurrent()).thenReturn(user);
        when(actividadMapper.toEntity(any())).thenReturn(entity);
        when(actividadRepository.save(any())).thenReturn(entity);
        when(actividadMapper.toDto(entity)).thenReturn(new ActividadDto(entity.getId(), dto.nombre(), dto.descripcion(), dto.tipo(), dto.horaInicio(), dto.horaFin(), dto.capacidadMaxima(), congresoDto, convocDto, userDto, dto.archivoUrl(), Estados.ACTIVO, entity.getCreatedAt(), entity.getUpdatedAt()));

        var result = actividadService.create(dto);
        assertNotNull(result);
        assertEquals(10L, result.id());
    }
}
