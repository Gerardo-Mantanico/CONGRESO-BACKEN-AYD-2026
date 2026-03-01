
package com.congreso.web.controller;

import com.congreso.domain.dto.congreso.CreateDtoCongreso;
import com.congreso.domain.dto.congreso.CongresoResponseDto;
import com.congreso.domain.service.CongresoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/congresos")
@Tag(name = "Congresos")
public class CongresoController {
    private final CongresoService congresoService;

    public CongresoController(CongresoService congresoService) {
        this.congresoService = congresoService;
    }

    @GetMapping
    public ResponseEntity<List<CongresoResponseDto>> list() {
        return ResponseEntity.ok(congresoService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CongresoResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(congresoService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo congreso")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CongresoResponseDto> create(@RequestBody @Valid CreateDtoCongreso dto) {
        return ResponseEntity.ok(congresoService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CongresoResponseDto> update(@PathVariable Long id, @RequestBody @Valid CreateDtoCongreso dto) {
        return ResponseEntity.ok(congresoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLogical(@PathVariable Long id) {
        congresoService.deleteLogical(id);
        return ResponseEntity.noContent().build();
    }
}

