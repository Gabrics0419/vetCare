package com.vetcare.backend.repository;

import com.vetcare.backend.model.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    Optional<Medicamento> findByNombre(String nombre);

    // Buscar medicamentos con stock bajo (menor que cierta cantidad)
    List<Medicamento> findByStockLessThan(Integer cantidad);
}
