package com.vetcare.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Table(name = "medicamentos")
@Data
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String nombre;

    private String descripcion;

    private Integer stock;

    @Column(precision = 10, scale = 2)
    private BigDecimal precioVenta;
}
