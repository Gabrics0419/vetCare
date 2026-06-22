package com.vetcare.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TratamientoRequest {

    private String descripcion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @Positive(message = "El costo debe ser mayor a cero")
    private BigDecimal costo;

    @NotNull(message = "Debe asociar una mascota")
    private Long mascotaId;

    @NotNull(message = "Debe asociar un veterinario")
    private Long veterinarioId;
}
