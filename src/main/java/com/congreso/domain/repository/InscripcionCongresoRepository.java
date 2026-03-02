package com.congreso.domain.repository;

import com.congreso.persistence.entity.InscripcionCongresoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionCongresoRepository extends JpaRepository<InscripcionCongresoEntity, Long> {
    boolean existsByCongresoId(Long congresoId);

    boolean existsByUsuarioIdAndCongresoId(Long usuarioId, Long congresoId);

    long countByCongresoId(Long congresoId);

    List<InscripcionCongresoEntity> findByUsuarioId(Long usuarioId);
}
