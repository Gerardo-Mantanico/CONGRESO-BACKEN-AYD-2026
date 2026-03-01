package com.congreso.web.controller;

import com.congreso.domain.dto.actividad.CreateActividadDto;
import com.congreso.domain.dto.actividad.ActividadDto;
import com.congreso.domain.dto.actividad.UpdateActividadDto;
import com.congreso.domain.service.ActividadService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/actividades")
@AllArgsConstructor
public class ActividadController {
    private final ActividadService actividadService;

    @PostMapping
    public ResponseEntity<ActividadDto> create(@RequestBody CreateActividadDto dto) {
        ActividadDto created = actividadService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActividadDto> update(@PathVariable Long id, @RequestBody UpdateActividadDto dto) {
        ActividadDto updated = actividadService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        actividadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

