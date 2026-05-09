package com.vetcare.backend.dto.request;

import com.vetcare.backend.model.Cita;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaRequest {

    @NotNull(message = "La fecha y hora de la cita es obligatoria")
    @Future(message = "La fecha de la cita debe ser futura")
    private LocalDateTime fechaHora;

    private String motivo;

    @NotNull(message = "El estado de la cita es obligatorio")
    private Cita.EstadoCita estado;

    @NotNull(message = "Debe seleccionar una mascota")
    private Long mascotaId;

    @NotNull(message = "Debe seleccionar un veterinario")
    private Long veterinarioId;
}
