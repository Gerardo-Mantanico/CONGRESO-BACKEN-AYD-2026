package com.congreso.web.controller;

import com.congreso.domain.dto.cuenta.CuentaDigitalCreationDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalDto;
import com.congreso.domain.dto.cuenta.CuentaDigitalUpdateDto;
import com.congreso.domain.service.CuentaDigitalService;
import com.congreso.web.response.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cuentas-digitales")
@AllArgsConstructor
public class CuentaDigitalController {
    private final CuentaDigitalService cuentaDigitalService;


    @GetMapping()
    public ResponseEntity<CuentaDigitalDto> getById() {
        return ResponseEntity.ok(cuentaDigitalService.getById());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDigitalDto> update(@PathVariable Long id, @RequestBody CuentaDigitalUpdateDto dto) {
        return ResponseEntity.ok(cuentaDigitalService.update(id, dto));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cuentaDigitalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

