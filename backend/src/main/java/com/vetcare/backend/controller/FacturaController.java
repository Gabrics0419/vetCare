package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.FacturaRequest;
import com.vetcare.backend.dto.response.FacturaResponse;
import com.vetcare.backend.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMIN')")
    public ResponseEntity<FacturaResponse> crear(@Valid @RequestBody FacturaRequest request) {
        FacturaResponse response = facturaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMIN')")
    public ResponseEntity<FacturaResponse> obtenerPorId(@PathVariable Long id) {
        FacturaResponse response = facturaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMIN')")
    public ResponseEntity<List<FacturaResponse>> listarTodas() {
        List<FacturaResponse> responses = facturaService.listarTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasRole('RECEPCIONISTA') or hasRole('ADMIN')")
    public ResponseEntity<List<FacturaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        List<FacturaResponse> responses = facturaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        facturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
