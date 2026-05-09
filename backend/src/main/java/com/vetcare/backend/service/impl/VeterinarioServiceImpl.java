package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.VeterinarioRequest;
import com.vetcare.backend.dto.response.VeterinarioResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Especialidad;
import com.vetcare.backend.model.Veterinario;
import com.vetcare.backend.repository.EspecialidadRepository;
import com.vetcare.backend.repository.VeterinarioRepository;
import com.vetcare.backend.service.VeterinarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VeterinarioServiceImpl implements VeterinarioService {

    private final VeterinarioRepository veterinarioRepository;
    private final EspecialidadRepository especialidadRepository;

    @Override
    @Transactional
    public VeterinarioResponse crear(VeterinarioRequest request) {
        Veterinario veterinario = new Veterinario();
        veterinario.setNombre(request.getNombre());
        veterinario.setApellido(request.getApellido());
        veterinario.setEmail(request.getEmail());
        veterinario.setTelefono(request.getTelefono());

        if (request.getEspecialidadId() != null) {
            Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + request.getEspecialidadId()));
            veterinario.setEspecialidad(especialidad);
        }

        Veterinario guardado = veterinarioRepository.save(veterinario);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public VeterinarioResponse obtenerPorId(Long id) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + id));
        return mapToResponse(veterinario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VeterinarioResponse> listarTodos() {
        return veterinarioRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VeterinarioResponse actualizar(Long id, VeterinarioRequest request) {
        Veterinario veterinario = veterinarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + id));

        veterinario.setNombre(request.getNombre());
        veterinario.setApellido(request.getApellido());
        veterinario.setEmail(request.getEmail());
        veterinario.setTelefono(request.getTelefono());

        if (request.getEspecialidadId() != null) {
            Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Especialidad no encontrada con id: " + request.getEspecialidadId()));
            veterinario.setEspecialidad(especialidad);
        } else {
            veterinario.setEspecialidad(null);
        }

        Veterinario actualizado = veterinarioRepository.save(veterinario);
        return mapToResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!veterinarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Veterinario no encontrado con id: " + id);
        }
        veterinarioRepository.deleteById(id);
    }

    private VeterinarioResponse mapToResponse(Veterinario veterinario) {
        VeterinarioResponse response = new VeterinarioResponse();
        response.setId(veterinario.getId());
        response.setNombre(veterinario.getNombre());
        response.setApellido(veterinario.getApellido());
        response.setEmail(veterinario.getEmail());
        response.setTelefono(veterinario.getTelefono());
        if (veterinario.getEspecialidad() != null) {
            response.setEspecialidadId(veterinario.getEspecialidad().getId());
            response.setEspecialidadNombre(veterinario.getEspecialidad().getNombre());
        }
        return response;
    }
}
