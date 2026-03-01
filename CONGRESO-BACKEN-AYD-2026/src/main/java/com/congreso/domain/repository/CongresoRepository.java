package com.congreso.domain.repository;

import com.congreso.persistence.entity.CongresoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CongresoRepository extends JpaRepository<CongresoEntity, Long> {
    List<CongresoEntity> findByActivoTrue();
    Optional<CongresoEntity> findByIdAndActivoTrue(Long id);
}

