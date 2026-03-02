package com.congreso.domain.service;

import com.congreso.domain.dto.inscripcion.CreateInscripcionDto;
import com.congreso.domain.dto.inscripcion.InscripcionResponseDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoRepository;
import com.congreso.domain.repository.InscripcionCongresoTipoRepository;
import com.congreso.domain.repository.PagoRepository;
import com.congreso.persistence.entity.InscripcionCongresoEntity;
import com.congreso.persistence.entity.InscripcionCongresoTipoEntity;
import com.congreso.persistence.entity.PagoEntity;
import com.congreso.persistence.entity.CongresoEntity;
import com.congreso.persistence.mapper.InscripcionMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InscripcionCongresoService {
    private final InscripcionCongresoRepository inscripcionRepo;
    private final InscripcionCongresoTipoRepository tipoRepo;
    private final PagoRepository pagoRepo;
    private final CuentaDigitalService cuentaService;
    private final CongresoRepository congresoRepo;

    @Transactional
    public InscripcionResponseDto create(CreateInscripcionDto dto) {
        Long usuarioId = dto.usuarioId();
        Long congresoId = dto.congresoId();
        List<String> tipos = dto.tipos() == null ? List.of("ASISTENTE") : dto.tipos();

        // Regla de unicidad (pre-check)
        if (inscripcionRepo.existsByUsuarioIdAndCongresoId(usuarioId, congresoId)) {
            throw new DomainException(HttpStatus.CONFLICT, "Usuario ya inscrito en este congreso");
        }

        CongressoCheck:
        {
        }

        CongresoEntity congreso = congresoRepo.findById(congresoId)
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));

        // Regla de temporalidad
        if (congreso.getActivo() == null || !congreso.getActivo()) {
            throw new DomainException(HttpStatus.CONFLICT, "No se permiten inscripciones: congreso inactivo");
        }
        if (!OffsetDateTime.now().isBefore(congreso.getFechaInicio())) {
            throw new DomainException(HttpStatus.CONFLICT, "No se permiten inscripciones: fecha límite excedida");
        }

        // Regla de cupos (si existe campo capacidad en entidad)
        // Si la entidad no tiene capacidad, omitimos esta validación
        // Recomendar bloqueo si hay alta concurrencia
        // Aquí hacemos conteo simple
        try {
            long capacidad = 0;
            // intentamos leer un campo 'capacidad' si existe (reflexión segura)
            try {
                java.lang.reflect.Field f = CongresoEntity.class.getDeclaredField("capacidad");
                f.setAccessible(true);
                Object val = f.get(congreso);
                if (val instanceof Number) capacidad = ((Number) val).longValue();
            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                capacidad = 0;
            }

            if (capacidad > 0) {
                long inscritos = inscripcionRepo.countByCongresoId(congresoId);
                if (inscritos >= capacidad) {
                    throw new DomainException(HttpStatus.CONFLICT, "Cupos agotados");
                }
            }

            // Transacción atomica: A->E
            BigDecimal precio = congreso.getPrecioInscripcion();

            // A: verificar saldo
            cuentaService.debitCuentaByUsuarioId(usuarioId, precio);

            // C: crear inscripcion
            InscripcionCongresoEntity ins = new InscripcionCongresoEntity();
            ins.setUsuarioId(usuarioId);
            ins.setCongresoId(congresoId);
            ins.setFechaInscripcion(OffsetDateTime.now());
            InscripcionCongresoEntity saved = inscripcionRepo.save(ins);

            // D: guardar tipos
            List<InscripcionCongresoTipoEntity> tiposEntities = new ArrayList<>();
            List<String> dedupTipos = tipos.stream().distinct().collect(Collectors.toList());
            for (String t : dedupTipos) {
                InscripcionCongresoTipoEntity te = new InscripcionCongresoTipoEntity();
                te.setInscripcionId(saved.getId());
                te.setTipoParticipacion(t);
                tiposEntities.add(te);
            }
            tipoRepo.saveAll(tiposEntities);

            // E: crear pago
            PagoEntity pago = new PagoEntity();
            pago.setInscripcionId(saved.getId());
            pago.setMontoTotal(precio);
            pago.setComisionPorcentaje(congreso.getComisionPorcentaje() == null ? BigDecimal.ZERO : congreso.getComisionPorcentaje());
            pago.setMontoComision(precio.multiply(pago.getComisionPorcentaje()).divide(new BigDecimal("100")));
            pago.setFechaPago(Instant.now());
            PagoEntity pagoSaved = pagoRepo.save(pago);

            List<InscripcionCongresoTipoEntity> savedTipos = tipoRepo.findByInscripcionId(saved.getId());

            return InscripcionMapper.entityToDto(saved, savedTipos, pagoSaved.getId());

        } catch (DataIntegrityViolationException ex) {
            // Fallback: DB uniqueness violation
            throw new DomainException(HttpStatus.CONFLICT, "Usuario ya inscrito en este congreso");
        } catch (RuntimeException ex) {
            // Si ocurre cualquier runtime exception en E, la transacción hará rollback y debemos propagar
            throw ex;
        }
    }

    public List<InscripcionResponseDto> getByUsuario(Long usuarioId) {
        List<InscripcionCongresoEntity> list = inscripcionRepo.findByUsuarioId(usuarioId);
        return list.stream().map(i -> {
            List<InscripcionCongresoTipoEntity> tipos = tipoRepo.findByInscripcionId(i.getId());
            Optional<PagoEntity> pago = pagoRepo.findAll().stream().filter(p -> p.getInscripcionId().equals(i.getId())).findFirst();
            return InscripcionMapper.entityToDto(i, tipos, pago.map(PagoEntity::getId).orElse(null));
        }).toList();
    }

    @Transactional
    public InscripcionResponseDto addTipos(Long inscripcionId, List<String> tipos) {
        InscripcionCongresoEntity ins = inscripcionRepo.findById(inscripcionId)
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Inscripcion no encontrada"));
        List<InscripcionCongresoTipoEntity> existing = tipoRepo.findByInscripcionId(inscripcionId);
        List<String> existingTipos = existing.stream().map(InscripcionCongresoTipoEntity::getTipoParticipacion).toList();
        List<InscripcionCongresoTipoEntity> toSave = new ArrayList<>();
        for (String t : tipos) {
            if (!existingTipos.contains(t)) {
                InscripcionCongresoTipoEntity te = new InscripcionCongresoTipoEntity();
                te.setInscripcionId(inscripcionId);
                te.setTipoParticipacion(t);
                toSave.add(te);
            }
        }
        tipoRepo.saveAll(toSave);
        List<InscripcionCongresoTipoEntity> savedTipos = tipoRepo.findByInscripcionId(inscripcionId);
        Optional<PagoEntity> pago = pagoRepo.findAll().stream().filter(p -> p.getInscripcionId().equals(inscripcionId)).findFirst();
        return InscripcionMapper.entityToDto(ins, savedTipos, pago.map(PagoEntity::getId).orElse(null));
    }
}

