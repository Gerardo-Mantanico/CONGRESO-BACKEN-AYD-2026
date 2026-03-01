package com.congreso.web.controller;

import com.congreso.domain.dto.institucion.CreateDtoInstitucion;
import com.congreso.domain.dto.institucion.InstitucionResponseDto;
import com.congreso.domain.service.InstitucionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("instituciones")
@Tag(name = "Instituciones")
public class InstitucionController {
    private final InstitucionService institucionService;

    public InstitucionController(InstitucionService institucionService) {
        this.institucionService = institucionService;
    }

    @GetMapping()
    public ResponseEntity<List<InstitucionResponseDto>> getAllInstituciones() {
        return ResponseEntity.ok(this.institucionService.listInstitucion());
    }

    @PostMapping()
    @Operation(summary = "Crea una nueva institución")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InstitucionResponseDto> createInstitucion(@RequestBody @Valid CreateDtoInstitucion institucion) {
        return ResponseEntity.ok(this.institucionService.create(institucion));
    }

    @Operation(summary = "Actualiza una institución por su ID")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<InstitucionResponseDto> updateInstitucion(@PathVariable Long id, @RequestBody @Valid CreateDtoInstitucion institucion) {
        return ResponseEntity.ok(this.institucionService.update(id, institucion));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Elimina una institución por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInstitucion(@PathVariable Long id) {
        this.institucionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
