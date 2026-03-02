package com.congreso.domain.repository;

import com.congreso.persistence.entity.ActividadEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;

public interface ActividadRepository extends JpaRepository<ActividadEntity, Long> {
    @Query("select case when count(a)>0 then true else false end from ActividadEntity a " +
            "where a.salonId = :salonId and a.activo = true and a.horaInicio < :horaFin and a.horaFin > :horaInicio")
    boolean existsOverlap(@Param("salonId") Long salonId, @Param("horaInicio") OffsetDateTime horaInicio, @Param("horaFin") OffsetDateTime horaFin);

    long countAsistenciasById(Long actividadId);
}

