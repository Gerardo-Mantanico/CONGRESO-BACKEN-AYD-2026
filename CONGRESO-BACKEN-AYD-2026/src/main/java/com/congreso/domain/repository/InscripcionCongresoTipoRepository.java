package com.congreso.domain.repository;

import com.congreso.persistence.entity.InscripcionCongresoTipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionCongresoTipoRepository extends JpaRepository<InscripcionCongresoTipoEntity, Long> {
    List<InscripcionCongresoTipoEntity> findByInscripcionId(Long inscripcionId);
}

