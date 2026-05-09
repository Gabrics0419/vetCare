package com.vetcare.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class FacturaRequest {

    @NotBlank(message = "El número de factura es obligatorio")
    private String numeroFactura;

    @NotNull(message = "La fecha de emisión es obligatoria")
    private LocalDateTime fechaEmision;

    @NotNull(message = "El subtotal es obligatorio")
    @PositiveOrZero(message = "El subtotal no puede ser negativo")
    private BigDecimal subtotal;

    @NotNull(message = "El impuesto es obligatorio")
    @PositiveOrZero(message = "El impuesto no puede ser negativo")
    private BigDecimal impuesto;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private BigDecimal total;

    @NotNull(message = "Debe asociar un cliente")
    private Long clienteId;
}
