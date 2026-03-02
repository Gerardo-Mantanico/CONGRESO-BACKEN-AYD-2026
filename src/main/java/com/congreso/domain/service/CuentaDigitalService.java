package com.congreso.domain.service;

import com.congreso.domain.dto.cuenta.CuentaDigitalCreationDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalUpdateDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.CuentaDigitalRepository;
import com.congreso.persistence.entity.CuentaDigitalEntity;
import com.congreso.persistence.mapper.CuentaDigitalMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@AllArgsConstructor
public class CuentaDigitalService {
    private final CuentaDigitalRepository cuentaDigitalRepository;
    private final CuentaDigitalMapper cuentaDigitalMapper;

    @Transactional
    public CuentaDigitalDto create(CuentaDigitalCreationDto dto) {
        if (cuentaDigitalRepository.findByUsuarioId(dto.getUsuarioId()).isPresent()) {
            throw new GeneralException("cuenta-exists", "El usuario ya tiene una cuenta digital");
        }
        CuentaDigitalEntity entity = cuentaDigitalMapper.toEntity(dto);
        CuentaDigitalEntity saved = cuentaDigitalRepository.save(entity);
        return cuentaDigitalMapper.toDto(saved);
    }

    @Transactional
    public CuentaDigitalDto update(Long id, CuentaDigitalUpdateDto dto) {
        CuentaDigitalEntity entity = cuentaDigitalRepository.findById(id)
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta no encontrada"));
        cuentaDigitalMapper.updateFromDto(dto, entity);
        CuentaDigitalEntity updated = cuentaDigitalRepository.save(entity);
        return cuentaDigitalMapper.toDto(updated);
    }

    public CuentaDigitalDto getById(Long id) {
        return cuentaDigitalRepository.findById(id)
                .map(cuentaDigitalMapper::toDto)
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta no encontrada"));
    }

    public Page<CuentaDigitalDto> getAll(Pageable pageable) {
        return cuentaDigitalRepository.findAll(pageable).map(cuentaDigitalMapper::toDto);
    }

    @Transactional
    public void delete(Long id) {
        CuentaDigitalEntity entity = cuentaDigitalRepository.findById(id)
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta no encontrada"));
        entity.setActiva(false);
        cuentaDigitalRepository.save(entity);
    }

    // New: debit and credit methods used by Inscripcion service
    @Transactional
    public void debitCuentaByUsuarioId(Long usuarioId, java.math.BigDecimal amount) {
        CuentaDigitalEntity entity = cuentaDigitalRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta digital no encontrada"));
        if (entity.getSaldo().compareTo(amount) < 0) {
            throw new GeneralException("saldo-insuficiente", "Saldo insuficiente");
        }
        entity.setSaldo(entity.getSaldo().subtract(amount));
        entity.setUpdatedAt(java.time.Instant.now());
        cuentaDigitalRepository.save(entity);
    }

    @Transactional
    public void creditCuentaByUsuarioId(Long usuarioId, java.math.BigDecimal amount) {
        CuentaDigitalEntity entity = cuentaDigitalRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta digital no encontrada"));
        entity.setSaldo(entity.getSaldo().add(amount));
        entity.setUpdatedAt(java.time.Instant.now());
        cuentaDigitalRepository.save(entity);
    }
}
