package com.vetcare.backend.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioResponse {
    private Long id;
    private String username;
    private String email;
    private Set<String> roles;
}
