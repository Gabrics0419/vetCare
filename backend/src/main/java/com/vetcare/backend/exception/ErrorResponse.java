package com.vetcare.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String timestamp;   // Cambio: String en lugar de LocalDateTime
    private int status;
    private String error;
    private String message;
    private String path;
}