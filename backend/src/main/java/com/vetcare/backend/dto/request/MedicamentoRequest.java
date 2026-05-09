package com.vetcare.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class MedicamentoRequest {

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "El precio de venta es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private BigDecimal precioVenta;
}
