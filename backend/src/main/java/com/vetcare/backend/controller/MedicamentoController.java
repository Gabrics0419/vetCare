package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.MedicamentoRequest;
import com.vetcare.backend.dto.response.MedicamentoResponse;
import com.vetcare.backend.service.MedicamentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @PostMapping
    public ResponseEntity<MedicamentoResponse> crear(@Valid @RequestBody MedicamentoRequest request) {
        MedicamentoResponse response = medicamentoService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> obtenerPorId(@PathVariable Long id) {
        MedicamentoResponse response = medicamentoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MedicamentoResponse>> listarTodos() {
        List<MedicamentoResponse> responses = medicamentoService.listarTodos();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicamentoResponse> actualizar(@PathVariable Long id,
                                                          @Valid @RequestBody MedicamentoRequest request) {
        MedicamentoResponse response = medicamentoService.actualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
