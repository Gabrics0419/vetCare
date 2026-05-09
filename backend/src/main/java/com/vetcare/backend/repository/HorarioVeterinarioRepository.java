package com.vetcare.backend.repository;

import com.vetcare.backend.model.HorarioVeterinario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioVeterinarioRepository extends JpaRepository<HorarioVeterinario, Long> {

    List<HorarioVeterinario> findByVeterinarioId(Long veterinarioId);
}
