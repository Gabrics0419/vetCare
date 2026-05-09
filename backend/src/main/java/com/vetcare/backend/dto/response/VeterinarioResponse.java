package com.vetcare.backend.dto.response;

import lombok.Data;

@Data
public class VeterinarioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private Long especialidadId;
    private String especialidadNombre;
}
