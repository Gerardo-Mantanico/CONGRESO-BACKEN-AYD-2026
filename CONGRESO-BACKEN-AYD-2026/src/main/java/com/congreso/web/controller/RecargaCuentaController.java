package com.congreso.web.controller;

import com.congreso.domain.dto.recarga.RecargaCuentaCreationDto;
import com.congreso.domain.dto.recarga.RecargaCuentaDto;
import com.congreso.domain.service.RecargaCuentaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recargas")
@AllArgsConstructor
public class RecargaCuentaController {
    private final RecargaCuentaService recargaCuentaService;

    @PostMapping
    public ResponseEntity<RecargaCuentaDto> create(@RequestBody @Valid RecargaCuentaCreationDto dto) {
        return ResponseEntity.ok(recargaCuentaService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecargaCuentaDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(recargaCuentaService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<RecargaCuentaDto>> listByCuenta(@RequestParam Long cuentaId) {
        return ResponseEntity.ok(recargaCuentaService.listByCuentaId(cuentaId));
    }
}

