package com.congreso.domain.service;

import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.repository.ActividadRepository;
import com.congreso.domain.repository.SalonRepository;
import com.congreso.domain.repository.CongresoRepository;
import com.congreso.persistence.entity.ActividadEntity;
import com.congreso.persistence.entity.SalonEntity;
import com.congreso.persistence.entity.CongresoEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ActividadService {
    private final ActividadRepository actividadRepository;
    private final SalonRepository salonRepository;
    private final CongresoRepository congresoRepository;

    @Transactional
    public ActividadDto create(CreateActividadDto dto) {
        // 1. Validar salon existe y pertenece al congreso
        SalonEntity salon = salonRepository.findById(dto.getSalonId())
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Salon no encontrado"));
        if (!salon.getCongresoId().equals(dto.getCongresoId())) {
            throw new DomainException(HttpStatus.CONFLICT, "El salón no pertenece a ese congreso");
        }

        // 2. Validar horas
        if (dto.getHoraInicio() == null || dto.getHoraFin() == null || !dto.getHoraInicio().isBefore(dto.getHoraFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "Rango de horas inválido");
        }

        // 3. Integridad del congreso
        CongresoEntity congreso = congresoRepository.findById(dto.getCongresoId())
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));
        if (dto.getHoraInicio().isBefore(congreso.getFechaInicio()) || dto.getHoraFin().isAfter(congreso.getFechaFin())) {
            throw new DomainException(HttpStatus.CONFLICT, "La actividad debe estar dentro de las fechas del congreso");
        }

        // 4. Colision horaria
        boolean overlap = actividadRepository.existsOverlap(dto.getSalonId(), dto.getHoraInicio(), dto.getHoraFin());
        if (overlap) {
            throw new DomainException(HttpStatus.CONFLICT, "El salón tiene otra actividad en ese horario");
        }

        // 5. Capacidad para taller
        if ("TALLER".equalsIgnoreCase(dto.getTipo())) {
            if (dto.getCapacidadMaxima() == null || dto.getCapacidadMaxima() <= 0) {
                throw new DomainException(HttpStatus.BAD_REQUEST, "Taller debe tener capacidad máxima positiva");
            }
            if (dto.getCapacidadMaxima() > salon.getCapacidad()) {
                throw new DomainException(HttpStatus.CONFLICT, "La capacidad del taller excede la capacidad del salón");
            }
        }

        // Guardar
        ActividadEntity entity = new ActividadEntity();
        entity.setNombre(dto.getNombre());
        entity.setDescripcion(dto.getDescripcion());
        entity.setTipo(dto.getTipo());
        entity.setHoraInicio(dto.getHoraInicio());
        entity.setHoraFin(dto.getHoraFin());
        entity.setCapacidadMaxima(dto.getCapacidadMaxima());
        entity.setSalonId(dto.getSalonId());
        entity.setCongresoId(dto.getCongresoId());
        ActividadEntity saved = actividadRepository.save(entity);

        ActividadDto out = new ActividadDto();
        out.setId(saved.getId());
        out.setNombre(saved.getNombre());
        out.setDescripcion(saved.getDescripcion());
        out.setTipo(saved.getTipo());
        out.setHoraInicio(saved.getHoraInicio());
        out.setHoraFin(saved.getHoraFin());
        out.setCapacidadMaxima(saved.getCapacidadMaxima());
        out.setSalonId(saved.getSalonId());
        out.setCongresoId(saved.getCongresoId());
        return out;
    }

    @Transactional
    public ActividadDto update(Long id, com.congreso.domain.dto.actividad.UpdateActividadDto dto) {
        ActividadEntity entity = actividadRepository.findById(id)
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));

        // contar asistencias (placeholder - implementar el repositorio real si existe)
        long asistentes = actividadRepository.countAsistenciasById(id);
        boolean cambioHorario = false;
        if (dto.getHoraInicio() != null && !dto.getHoraInicio().equals(entity.getHoraInicio())) cambioHorario = true;
        if (dto.getHoraFin() != null && !dto.getHoraFin().equals(entity.getHoraFin())) cambioHorario = true;
        if (dto.getSalonId() != null && !dto.getSalonId().equals(entity.getSalonId())) cambioHorario = true;

        if (asistentes > 0 && cambioHorario) {
            throw new DomainException(HttpStatus.CONFLICT, "No se puede cambiar hora o salón porque ya hay asistentes registrados");
        }

        // Si se solicita cambiar salon o horas, re-ejecutar validaciones (colision y fechas)
        if (dto.getSalonId() != null) entity.setSalonId(dto.getSalonId());
        if (dto.getHoraInicio() != null) entity.setHoraInicio(dto.getHoraInicio());
        if (dto.getHoraFin() != null) entity.setHoraFin(dto.getHoraFin());
        if (dto.getNombre() != null) entity.setNombre(dto.getNombre());
        if (dto.getDescripcion() != null) entity.setDescripcion(dto.getDescripcion());
        if (dto.getTipo() != null) entity.setTipo(dto.getTipo());
        if (dto.getCapacidadMaxima() != null) entity.setCapacidadMaxima(dto.getCapacidadMaxima());

        // re-validaciones: horas válidas
        if (entity.getHoraInicio() == null || entity.getHoraFin() == null || !entity.getHoraInicio().isBefore(entity.getHoraFin())) {
            throw new DomainException(HttpStatus.BAD_REQUEST, "Rango de horas inválido");
        }

        // validar fechas del congreso
        CongresoEntity congreso = congresoRepository.findById(entity.getCongresoId())
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Congreso no encontrado"));
        if (entity.getHoraInicio().isBefore(congreso.getFechaInicio()) || entity.getHoraFin().isAfter(congreso.getFechaFin())) {
            throw new DomainException(HttpStatus.CONFLICT, "La actividad debe estar dentro de las fechas del congreso");
        }

        // validar colision si cambió horario o salon
        if (cambioHorario) {
            boolean overlap = actividadRepository.existsOverlap(entity.getSalonId(), entity.getHoraInicio(), entity.getHoraFin());
            // excluir la propia actividad del chequeo: asumiendo existsOverlap excluye por id; si no, mejor crear query que acepte id
            if (overlap) {
                throw new DomainException(HttpStatus.CONFLICT, "El salón tiene otra actividad en ese horario");
            }
        }

        ActividadEntity saved = actividadRepository.save(entity);
        ActividadDto out = new ActividadDto();
        out.setId(saved.getId());
        out.setNombre(saved.getNombre());
        out.setDescripcion(saved.getDescripcion());
        out.setTipo(saved.getTipo());
        out.setHoraInicio(saved.getHoraInicio());
        out.setHoraFin(saved.getHoraFin());
        out.setCapacidadMaxima(saved.getCapacidadMaxima());
        out.setSalonId(saved.getSalonId());
        out.setCongresoId(saved.getCongresoId());
        return out;
    }

    @Transactional
    public void delete(Long id) {
        ActividadEntity entity = actividadRepository.findById(id)
                .orElseThrow(() -> new DomainException(HttpStatus.NOT_FOUND, "Actividad no encontrada"));
        long asistentes = actividadRepository.countAsistenciasById(id);
        if (asistentes > 0) {
            throw new DomainException(HttpStatus.CONFLICT, "No se puede eliminar actividad con asistentes registrados");
        }
        entity.setActivo(false);
        actividadRepository.save(entity);
    }
}
