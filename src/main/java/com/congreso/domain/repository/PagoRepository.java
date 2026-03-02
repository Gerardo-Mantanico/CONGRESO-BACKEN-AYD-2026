package com.congreso.domain.repository;

import com.congreso.persistence.entity.PagoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<PagoEntity, Long> {
}

