package com.vetcare.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_veterinario")
@Data
public class HorarioVeterinario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "veterinario_id", nullable = false)
    private Veterinario veterinario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek diaSemana;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;
}