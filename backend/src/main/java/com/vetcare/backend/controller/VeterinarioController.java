package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.VeterinarioRequest;
import com.vetcare.backend.dto.response.VeterinarioResponse;
import com.vetcare.backend.service.VeterinarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veterinarios")
@RequiredArgsConstructor
public class VeterinarioController {

    private final VeterinarioService veterinarioService;

    @PostMapping
    public ResponseEntity<VeterinarioResponse> crear(@Valid @RequestBody VeterinarioRequest request) {
        VeterinarioResponse response = veterinarioService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> obtenerPorId(@PathVariable Long id) {
        VeterinarioResponse response = veterinarioService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VeterinarioResponse>> listarTodos() {
        List<VeterinarioResponse> responses = veterinarioService.listarTodos();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeterinarioResponse> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody VeterinarioRequest request) {
        VeterinarioResponse response = veterinarioService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        veterinarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
