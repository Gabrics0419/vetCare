package com.vetcare.backend.repository;

import com.vetcare.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // Método personalizado para buscar cliente por email
    Optional<Cliente> findByEmail(String email);
}
