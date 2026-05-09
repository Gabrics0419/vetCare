package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.TratamientoRequest;
import com.vetcare.backend.dto.response.TratamientoResponse;
import com.vetcare.backend.service.TratamientoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@RequiredArgsConstructor
public class TratamientoController {

    private final TratamientoService tratamientoService;

    @PostMapping
    public ResponseEntity<TratamientoResponse> crear(@Valid @RequestBody TratamientoRequest request) {
        TratamientoResponse response = tratamientoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TratamientoResponse> obtenerPorId(@PathVariable Long id) {
        TratamientoResponse response = tratamientoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TratamientoResponse>> listarTodos() {
        List<TratamientoResponse> responses = tratamientoService.listarTodos();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/mascota/{mascotaId}")
    public ResponseEntity<List<TratamientoResponse>> listarPorMascota(@PathVariable Long mascotaId) {
        List<TratamientoResponse> responses = tratamientoService.listarPorMascota(mascotaId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/veterinario/{veterinarioId}")
    public ResponseEntity<List<TratamientoResponse>> listarPorVeterinario(@PathVariable Long veterinarioId) {
        List<TratamientoResponse> responses = tratamientoService.listarPorVeterinario(veterinarioId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TratamientoResponse> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody TratamientoRequest request) {
        TratamientoResponse response = tratamientoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        tratamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
