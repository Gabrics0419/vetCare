package com.vetcare.backend.controller;

import com.vetcare.backend.dto.request.LoginRequest;
import com.vetcare.backend.dto.request.RegisterRequest;
import com.vetcare.backend.dto.response.AuthResponse;
import com.vetcare.backend.model.Rol;
import com.vetcare.backend.model.Usuario;
import com.vetcare.backend.repository.RolRepository;
import com.vetcare.backend.repository.UsuarioRepository;
import com.vetcare.backend.security.jwt.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails);
        Usuario usuario = usuarioRepository.findByUsername(userDetails.getUsername()).orElseThrow();

        return ResponseEntity.ok(new AuthResponse(
                token,
                usuario.getUsername(),
                usuario.getEmail(),
                "Login exitoso"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, null, null, "El username ya existe"));
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, null, null, "El email ya existe"));
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));

        Rol rolDefault = rolRepository.findByNombre("RECEPCIONISTA")
                .orElseGet(() -> {
                    Rol nuevoRol = new Rol();
                    nuevoRol.setNombre("RECEPCIONISTA");
                    nuevoRol.setDescripcion("Rol por defecto");
                    return rolRepository.save(nuevoRol);
                });
        Set<Rol> roles = new HashSet<>();
        roles.add(rolDefault);
        usuario.setRoles(roles);

        usuarioRepository.save(usuario);

        UserDetails userDetails = usuario;
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, usuario.getUsername(), usuario.getEmail(), "Usuario registrado exitosamente"));
    }
}
