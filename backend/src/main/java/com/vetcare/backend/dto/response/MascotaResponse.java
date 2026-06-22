package com.vetcare.backend.dto.response;

import lombok.Data;
import java.time.LocalDate;

@Data
public class MascotaResponse {
    private Long id;
    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    private Long clienteId;
    private String clienteNombreCompleto; // Campo calculado para mostrar info útil
}
