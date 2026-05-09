package com.vetcare.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "especialidades")
@Data
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String nombre;

    private String descripcion;
}
