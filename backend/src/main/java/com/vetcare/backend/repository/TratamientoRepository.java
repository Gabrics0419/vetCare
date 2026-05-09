package com.vetcare.backend.repository;

import com.vetcare.backend.model.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {

    List<Tratamiento> findByMascotaId(Long mascotaId);
    List<Tratamiento> findByVeterinarioId(Long veterinarioId);
}
