package com.vetcare.backend.controller;

import com.vetcare.backend.model.Especialidad;
import com.vetcare.backend.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(especialidadRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Especialidad> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada")));
    }

    @PostMapping
    public ResponseEntity<Especialidad> crear(@RequestBody Especialidad especialidad) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(especialidadRepository.save(especialidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Especialidad> actualizar(@PathVariable Long id,
                                                   @RequestBody Especialidad especialidad) {
        Especialidad existente = especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));
        existente.setNombre(especialidad.getNombre());
        existente.setDescripcion(especialidad.getDescripcion());
        return ResponseEntity.ok(especialidadRepository.save(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        especialidadRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}