package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.FacturaRequest;
import com.vetcare.backend.dto.response.FacturaResponse;
import com.vetcare.backend.service.FacturaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaController {

    private final FacturaService facturaService;

    @PostMapping
    public ResponseEntity<FacturaResponse> crear(@Valid @RequestBody FacturaRequest request) {
        FacturaResponse response = facturaService.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacturaResponse> obtenerPorId(@PathVariable Long id) {
        FacturaResponse response = facturaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FacturaResponse>> listarTodas() {
        List<FacturaResponse> responses = facturaService.listarTodas();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FacturaResponse>> listarPorCliente(@PathVariable Long clienteId) {
        List<FacturaResponse> responses = facturaService.listarPorCliente(clienteId);
        return ResponseEntity.ok(responses);
    }

    // Normalmente no se actualiza una factura; podría omitirse, pero lo incluimos por completitud.
    // Si se desea, se puede dejar solo crear, consultar y eliminar (o anular).

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        facturaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
