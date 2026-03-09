package com.congreso.web.controller;

import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.service.ActividadService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/actividades")
@AllArgsConstructor
public class ActividadController {
    private final ActividadService actividadService;

    @PostMapping
    public ResponseEntity<ActividadDto> create(@RequestBody CreateActividadDto dto) {
        ActividadDto created = actividadService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActividadDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(actividadService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadDto> update(@PathVariable Long id, @RequestBody CreateActividadDto dto) {
        return ResponseEntity.ok(actividadService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ActividadDto>> listAll(Pageable pageable) {
        return ResponseEntity.ok(actividadService.listAll(pageable));
    }

    @GetMapping("/congreso/{congresoId}")
    public ResponseEntity<Page<ActividadDto>> listByCongreso(@PathVariable Long congresoId, Pageable pageable) {
        return ResponseEntity.ok(actividadService.listByCongreso(congresoId, pageable));
    }

    @GetMapping("/convocatoria/{convocatoriaId}")
    public ResponseEntity<Page<ActividadDto>> listByConvocatoria(@PathVariable Long convocatoriaId, Pageable pageable) {
        return ResponseEntity.ok(actividadService.listByConvocatoria(convocatoriaId, pageable));
    }
}

