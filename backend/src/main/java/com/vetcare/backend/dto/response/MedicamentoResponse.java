package com.vetcare.backend.dto.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class MedicamentoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private BigDecimal precioVenta;
}
