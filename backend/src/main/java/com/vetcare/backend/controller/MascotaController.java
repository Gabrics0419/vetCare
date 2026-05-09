package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.MascotaRequest;
import com.vetcare.backend.dto.response.MascotaResponse;
import com.vetcare.backend.service.MascotaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
public class MascotaController {

    private final MascotaService mascotaService;

    @PostMapping
    public ResponseEntity<MascotaResponse> crear(@Valid @RequestBody MascotaRequest request) {
        MascotaResponse response = mascotaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MascotaResponse> obtenerPorId(@PathVariable Long id) {
        MascotaResponse response = mascotaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MascotaResponse>> listarTodas() {
        List<MascotaResponse> responses = mascotaService.listarTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<MascotaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        List<MascotaResponse> responses = mascotaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MascotaResponse> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody MascotaRequest request) {
        MascotaResponse response = mascotaService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mascotaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
