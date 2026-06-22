package com.vetcare.backend;

import com.vetcare.backend.model.Rol;
import com.vetcare.backend.model.Usuario;
import com.vetcare.backend.repository.RolRepository;
import com.vetcare.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UsuarioRepository usuarioRepo,
	                                  RolRepository rolRepo,
	                                  PasswordEncoder encoder) {
		return args -> {
			Rol adminRol = rolRepo.findByNombre("ADMIN").orElseGet(() -> {
				Rol r = new Rol();
				r.setNombre("ADMIN");
				r.setDescripcion("Administrador del sistema");
				return rolRepo.save(r);
			});
			Rol vetRol = rolRepo.findByNombre("VETERINARIO").orElseGet(() -> {
				Rol r = new Rol();
				r.setNombre("VETERINARIO");
				r.setDescripcion("Médico veterinario");
				return rolRepo.save(r);
			});
			Rol recRol = rolRepo.findByNombre("RECEPCIONISTA").orElseGet(() -> {
				Rol r = new Rol();
				r.setNombre("RECEPCIONISTA");
				r.setDescripcion("Recepcionista");
				return rolRepo.save(r);
			});

			Usuario admin = usuarioRepo.findByUsername("admin").orElseGet(() -> {
				Usuario u = new Usuario();
				u.setUsername("admin");
				u.setEmail("admin@vetcare.com");
				u.setPassword(encoder.encode("admin123"));
				return u;
			});

			if (admin.getRoles() == null || admin.getRoles().isEmpty()) {
				admin.setRoles(Set.of(adminRol));
				usuarioRepo.save(admin);
				System.out.println("✅ Rol ADMIN asignado al usuario admin");
			} else if (!admin.getRoles().contains(adminRol)) {
				admin.getRoles().add(adminRol);
				usuarioRepo.save(admin);
				System.out.println("Rol ADMIN agregado al usuario admin");
			}
		};
	}
}