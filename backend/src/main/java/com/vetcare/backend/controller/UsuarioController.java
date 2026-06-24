package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.UsuarioRolesRequest;
import com.vetcare.backend.dto.response.UsuarioResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Rol;
import com.vetcare.backend.model.Usuario;
import com.vetcare.backend.repository.RolRepository;
import com.vetcare.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioResponse> responses = usuarios.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return ResponseEntity.ok(mapToResponse(usuario));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UsuarioResponse> actualizarRoles(
            @PathVariable Long id,
            @RequestBody UsuarioRolesRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        // Limpiar roles actuales
        usuario.getRoles().clear();

        // Asignar nuevos roles
        if (request.getRoles() != null) {
            for (String nombreRol : request.getRoles()) {
                Rol rol = rolRepository.findByNombre(nombreRol)
                        .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado: " + nombreRol));
                usuario.getRoles().add(rol);
            }
        }

        Usuario actualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioResponse mapToResponse(Usuario usuario) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(usuario.getId());
        response.setUsername(usuario.getUsername());
        response.setEmail(usuario.getEmail());
        response.setRoles(usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toSet()));
        return response;
    }
}
