package com.vetcare.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String nombre;  // Ej: ADMIN, VETERINARIO, RECEPCIONISTA

    private String descripcion;
}
