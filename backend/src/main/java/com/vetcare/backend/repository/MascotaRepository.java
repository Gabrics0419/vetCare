package com.vetcare.backend.repository;

import com.vetcare.backend.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {

    // Buscar todas las mascotas de un cliente específico
    List<Mascota> findByClienteId(Long clienteId);
}
