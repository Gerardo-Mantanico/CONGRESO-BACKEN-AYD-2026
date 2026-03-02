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

    @PostMapping
    public ResponseEntity<CuentaDigitalDto> create(@RequestBody @Valid CuentaDigitalCreationDto dto) {
        return ResponseEntity.ok(cuentaDigitalService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDigitalDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaDigitalService.getById(id));
    }

    @GetMapping
    public PageResponse<CuentaDigitalDto> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending
    ) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<CuentaDigitalDto> pageResult = cuentaDigitalService.getAll(pageable);
        return PageResponse.fromPage(pageResult);
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

