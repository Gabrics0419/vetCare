package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.CitaRequest;
import com.vetcare.backend.dto.response.CitaResponse;
import com.vetcare.backend.service.CitaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @PostMapping
    public ResponseEntity<CitaResponse> crear(@Valid @RequestBody CitaRequest request) {
        CitaResponse response = citaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitaResponse> obtenerPorId(@PathVariable Long id) {
        CitaResponse response = citaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CitaResponse>> listarTodas() {
        List<CitaResponse> responses = citaService.listarTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<CitaResponse>> listarPorMascota(@PathVariable Long mascotaId) {
        List<CitaResponse> responses = citaService.listarPorMascota(mascotaId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<CitaResponse>> listarPorVeterinario(@PathVariable Long veterinarioId) {
        List<CitaResponse> responses = citaService.listarPorVeterinario(veterinarioId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/rango")
    public ResponseEntity<List<CitaResponse>> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<CitaResponse> responses = citaService.listarPorRangoFechas(inicio, fin);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CitaResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody CitaRequest request) {
        CitaResponse response = citaService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
