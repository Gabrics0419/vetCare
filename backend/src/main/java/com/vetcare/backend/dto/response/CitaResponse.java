package com.vetcare.backend.dto.response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CitaResponse {
    private Long id;
    private LocalDateTime fechaHora;
    private String motivo;
    private String estado;
    private Long mascotaId;
    private String mascotaNombre;
    private Long veterinarioId;
    private String veterinarioNombre;
}
