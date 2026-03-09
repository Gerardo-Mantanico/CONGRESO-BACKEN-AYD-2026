package com.congreso.web.controller;

import com.congreso.domain.dto.convocatoria.CreateConvocatoriaDto;
import com.congreso.domain.dto.convocatoria.ConvocatoriaDto;
import com.congreso.domain.service.ConvocatoriaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convocatorias")
@AllArgsConstructor
public class ConvocatoriaController {
    private final ConvocatoriaService convocatoriaService;

    @PostMapping
    public ResponseEntity<ConvocatoriaDto> create(@RequestBody CreateConvocatoriaDto dto) {
        ConvocatoriaDto created = convocatoriaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConvocatoriaDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(convocatoriaService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConvocatoriaDto> update(@PathVariable Long id, @RequestBody CreateConvocatoriaDto dto) {
        return ResponseEntity.ok(convocatoriaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        convocatoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ConvocatoriaDto>> listAll(Pageable pageable) {
        Page<ConvocatoriaDto> page = convocatoriaService.listAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/congreso/{congresoId}")
    public ResponseEntity<Page<ConvocatoriaDto>> listByCongreso(@PathVariable Long congresoId, Pageable pageable) {
        Page<ConvocatoriaDto> page = convocatoriaService.listByCongreso(congresoId, pageable);
        return ResponseEntity.ok(page);
    }
}

