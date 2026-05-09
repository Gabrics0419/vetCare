package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.TratamientoRequest;
import com.vetcare.backend.dto.response.TratamientoResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Mascota;
import com.vetcare.backend.model.Tratamiento;
import com.vetcare.backend.model.Veterinario;
import com.vetcare.backend.repository.MascotaRepository;
import com.vetcare.backend.repository.TratamientoRepository;
import com.vetcare.backend.repository.VeterinarioRepository;
import com.vetcare.backend.service.TratamientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TratamientoServiceImpl implements TratamientoService {

    private final TratamientoRepository tratamientoRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    @Override
    @Transactional
    public TratamientoResponse crear(TratamientoRequest request) {
        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + request.getMascotaId()));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + request.getVeterinarioId()));

        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setDescripcion(request.getDescripcion());
        tratamiento.setFechaInicio(request.getFechaInicio());
        tratamiento.setFechaFin(request.getFechaFin());
        tratamiento.setCosto(request.getCosto());
        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(veterinario);

        Tratamiento guardado = tratamientoRepository.save(tratamiento);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public TratamientoResponse obtenerPorId(Long id) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tratamiento no encontrado con id: " + id));
        return mapToResponse(tratamiento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponse> listarTodos() {
        return tratamientoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponse> listarPorMascota(Long mascotaId) {
        if (!mascotaRepository.existsById(mascotaId)) {
            throw new ResourceNotFoundException("Mascota no encontrada con id: " + mascotaId);
        }
        return tratamientoRepository.findByMascotaId(mascotaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TratamientoResponse> listarPorVeterinario(Long veterinarioId) {
        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new ResourceNotFoundException("Veterinario no encontrado con id: " + veterinarioId);
        }
        return tratamientoRepository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TratamientoResponse actualizar(Long id, TratamientoRequest request) {
        Tratamiento tratamiento = tratamientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tratamiento no encontrado con id: " + id));

        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + request.getMascotaId()));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + request.getVeterinarioId()));

        tratamiento.setDescripcion(request.getDescripcion());
        tratamiento.setFechaInicio(request.getFechaInicio());
        tratamiento.setFechaFin(request.getFechaFin());
        tratamiento.setCosto(request.getCosto());
        tratamiento.setMascota(mascota);
        tratamiento.setVeterinario(veterinario);

        Tratamiento actualizado = tratamientoRepository.save(tratamiento);
        return mapToResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!tratamientoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tratamiento no encontrado con id: " + id);
        }
        tratamientoRepository.deleteById(id);
    }

    private TratamientoResponse mapToResponse(Tratamiento tratamiento) {
        TratamientoResponse response = new TratamientoResponse();
        response.setId(tratamiento.getId());
        response.setDescripcion(tratamiento.getDescripcion());
        response.setFechaInicio(tratamiento.getFechaInicio());
        response.setFechaFin(tratamiento.getFechaFin());
        response.setCosto(tratamiento.getCosto());
        response.setMascotaId(tratamiento.getMascota().getId());
        response.setMascotaNombre(tratamiento.getMascota().getNombre());
        response.setVeterinarioId(tratamiento.getVeterinario().getId());
        response.setVeterinarioNombre(tratamiento.getVeterinario().getNombre() + " " + tratamiento.getVeterinario().getApellido());
        return response;
    }
}
