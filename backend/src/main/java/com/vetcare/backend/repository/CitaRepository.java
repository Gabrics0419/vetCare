package com.vetcare.backend.repository;

import com.vetcare.backend.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Buscar citas por mascota
    List<Cita> findByMascotaId(Long mascotaId);

    // Buscar citas por veterinario
    List<Cita> findByVeterinarioId(Long veterinarioId);

    // Buscar citas en un rango de fechas (opcional, útil para agenda)
    List<Cita> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
}
