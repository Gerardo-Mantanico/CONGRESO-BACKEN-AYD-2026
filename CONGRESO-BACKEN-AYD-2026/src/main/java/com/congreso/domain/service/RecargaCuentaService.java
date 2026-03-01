package com.congreso.domain.service;

import com.congreso.domain.dto.recarga.RecargaCuentaCreationDto;
import com.congreso.domain.dto.recarga.RecargaCuentaDto;
import com.congreso.domain.exception.GeneralException;
import com.congreso.domain.repository.CuentaDigitalRepository;
import com.congreso.domain.repository.RecargaCuentaRepository;
import com.congreso.persistence.entity.CuentaDigitalEntity;
import com.congreso.persistence.entity.RecargaCuentaEntity;
import com.congreso.persistence.mapper.RecargaCuentaMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecargaCuentaService {
    private final RecargaCuentaRepository recargaCuentaRepository;
    private final CuentaDigitalRepository cuentaDigitalRepository;
    private final RecargaCuentaMapper recargaCuentaMapper;

    @Transactional
    public RecargaCuentaDto create(RecargaCuentaCreationDto dto) {
        CuentaDigitalEntity cuenta = cuentaDigitalRepository.findById(dto.getCuentaDigitalId())
                .orElseThrow(() -> new GeneralException("cuenta-not-found", "Cuenta digital no encontrada"));

        RecargaCuentaEntity recarga = recargaCuentaMapper.toEntity(dto);
        // Si la recarga se registra como COMPLETED en el DTO (o lógica externa), actualizar saldo
        if ("COMPLETED".equalsIgnoreCase(recarga.getEstado())) {
            BigDecimal nuevoSaldo = (cuenta.getSaldo() == null ? BigDecimal.ZERO : cuenta.getSaldo()).add(recarga.getMonto());
            cuenta.setSaldo(nuevoSaldo);
            cuentaDigitalRepository.save(cuenta);
        }

        RecargaCuentaEntity saved = recargaCuentaRepository.save(recarga);
        return recargaCuentaMapper.toDto(saved);
    }

    public RecargaCuentaDto getById(Long id) {
        return recargaCuentaRepository.findById(id)
                .map(recargaCuentaMapper::toDto)
                .orElseThrow(() -> new GeneralException("recarga-not-found", "Recarga no encontrada"));
    }

    public List<RecargaCuentaDto> listByCuentaId(Long cuentaId) {
        return recargaCuentaRepository.findAllByCuentaDigitalIdOrderByFechaRecargaDesc(cuentaId)
                .stream()
                .map(recargaCuentaMapper::toDto)
                .collect(Collectors.toList());
    }
}

