package com.vetcare.backend.dto.request;

import lombok.Data;
import java.util.Set;

@Data
public class UsuarioRolesRequest {
    private Set<String> roles;
}
