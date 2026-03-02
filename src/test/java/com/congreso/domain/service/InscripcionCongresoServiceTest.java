package com.congreso.domain.service;

import com.congreso.domain.dto.inscripcion.CreateInscripcionDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoTipoRepository;
import com.congreso.domain.repository.PagoRepository;
import com.congreso.persistence.entity.*;
import com.congreso.persistence.mapper.InscripcionMapper;
import com.congreso.test.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InscripcionCongresoServiceTest {

    @Mock
    private InscripcionCongresoRepository inscripcionRepo;

    @Mock
    private InscripcionCongresoTipoRepository tipoRepo;

    @Mock
    private PagoRepository pagoRepo;

    @Mock
    private CuentaDigitalService cuentaService;

    @Mock
    private CongresoRepository congresoRepo;

    @InjectMocks
    private InscripcionCongresoService inscripcionService;

    private CreateInscripcionDto dto;
    private CongresoEntity congreso;

    @BeforeEach
    void setUp() {
        dto = TestFixtures.createInscripcionDto();
        congreso = TestFixtures.createCongresoEntity();
    }

    @Test
    void create_whenUserAlreadyInscrito_throwsConflict() {
        when(inscripcionRepo.existsByUsuarioIdAndCongresoId(dto.usuarioId(), dto.congresoId())).thenReturn(true);
        DomainException ex = assertThrows(DomainException.class, () -> inscripcionService.create(dto));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
        verify(inscripcionRepo, never()).save(any());
    }

    @Test
    void create_whenCongresoNotFound_throwsNotFound() {
        when(inscripcionRepo.existsByUsuarioIdAndCongresoId(dto.usuarioId(), dto.congresoId())).thenReturn(false);
        when(congresoRepo.findById(dto.congresoId())).thenReturn(Optional.empty());
        DomainException ex = assertThrows(DomainException.class, () -> inscripcionService.create(dto));
        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    void create_whenCongresoInactive_throwsConflict() {
        when(inscripcionRepo.existsByUsuarioIdAndCongresoId(dto.usuarioId(), dto.congresoId())).thenReturn(false);
        congreso.setActivo(false);
        when(congresoRepo.findById(dto.congresoId())).thenReturn(Optional.of(congreso));
        DomainException ex = assertThrows(DomainException.class, () -> inscripcionService.create(dto));
        assertEquals(HttpStatus.CONFLICT, ex.getStatusCode());
    }

    @Test
    void create_happyPath_savesAndReturns() {
        when(inscripcionRepo.existsByUsuarioIdAndCongresoId(dto.usuarioId(), dto.congresoId())).thenReturn(false);
        when(congresoRepo.findById(dto.congresoId())).thenReturn(Optional.of(congreso));
        // cuentaService debe debitar sin excepcion
        doNothing().when(cuentaService).debitCuentaByUsuarioId(dto.usuarioId(), congreso.getPrecioInscripcion());

        InscripcionCongresoEntity insEntity = new InscripcionCongresoEntity();
        insEntity.setId(99L);
        insEntity.setUsuarioId(dto.usuarioId());
        insEntity.setCongresoId(dto.congresoId());

        when(inscripcionRepo.save(any(InscripcionCongresoEntity.class))).thenReturn(insEntity);

        PagoEntity pago = new PagoEntity();
        pago.setId(11L);
        pago.setInscripcionId(insEntity.getId());
        pago.setMontoTotal(congreso.getPrecioInscripcion());
        when(pagoRepo.save(any(PagoEntity.class))).thenReturn(pago);

        when(tipoRepo.saveAll(any())).thenReturn(List.of());
        when(tipoRepo.findByInscripcionId(insEntity.getId())).thenReturn(List.of());

        var res = inscripcionService.create(dto);
        assertNotNull(res);
        assertEquals(insEntity.getId(), res.id());
    }
}
