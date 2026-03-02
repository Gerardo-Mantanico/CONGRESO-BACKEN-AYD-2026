package com.congreso.web.controller;

import com.congreso.domain.dto.salon.CreateSalonDto;
import com.congreso.domain.dto.salon.SalonDto;
import com.congreso.domain.service.SalonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salones")
@AllArgsConstructor
public class SalonController {
    private final SalonService salonService;

    @PostMapping
    public ResponseEntity<SalonDto> create(@RequestBody CreateSalonDto dto) {
        SalonDto created = salonService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/congreso/{congresoId}")
    public ResponseEntity<List<SalonDto>> listByCongreso(@PathVariable Long congresoId) {
        List<SalonDto> list = salonService.listByCongreso(congresoId);
        return ResponseEntity.ok(list);
    }
}

