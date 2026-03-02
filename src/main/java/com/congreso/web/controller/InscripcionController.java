package com.congreso.web.controller;

import com.congreso.domain.dto.inscripcion.CreateInscripcionDto;
import com.congreso.domain.dto.inscripcion.InscripcionResponseDto;
import com.congreso.domain.exception.DomainException;
import com.congreso.domain.service.InscripcionCongresoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@AllArgsConstructor
public class InscripcionController {
    private final InscripcionCongresoService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDto> create(@RequestBody CreateInscripcionDto dto, Principal principal) {
        // Si tu sistema tiene autenticación, reemplazar usuarioId por el del token.
        InscripcionResponseDto created = inscripcionService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/mine")
    public ResponseEntity<List<InscripcionResponseDto>> mine(@RequestParam Long usuarioId) {
        List<InscripcionResponseDto> list = inscripcionService.getByUsuario(usuarioId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/admin/congresos/{congresoId}")
    public ResponseEntity<List<InscripcionResponseDto>> byCongresoForAdmin(@PathVariable Long congresoId) {
        // Implementar control de acceso administrador en filtros de seguridad
        // Por ahora, reutilizamos getByUsuario pasando null -> no es ideal
        return ResponseEntity.ok(List.of());
    }

    @PutMapping("/{id}/tipos")
    public ResponseEntity<InscripcionResponseDto> addTipos(@PathVariable Long id, @RequestBody List<String> tipos) {
        InscripcionResponseDto updated = inscripcionService.addTipos(id, tipos);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotAllowed(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }
}

