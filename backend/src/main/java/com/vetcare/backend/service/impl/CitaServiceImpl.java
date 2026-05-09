package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.CitaRequest;
import com.vetcare.backend.dto.response.CitaResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Cita;
import com.vetcare.backend.model.Mascota;
import com.vetcare.backend.model.Veterinario;
import com.vetcare.backend.repository.CitaRepository;
import com.vetcare.backend.repository.MascotaRepository;
import com.vetcare.backend.repository.VeterinarioRepository;
import com.vetcare.backend.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;
    private final MascotaRepository mascotaRepository;
    private final VeterinarioRepository veterinarioRepository;

    @Override
    @Transactional
    public CitaResponse crear(CitaRequest request) {
        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + request.getMascotaId()));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + request.getVeterinarioId()));

        Cita cita = new Cita();
        cita.setFechaHora(request.getFechaHora());
        cita.setMotivo(request.getMotivo());
        cita.setEstado(request.getEstado());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        Cita guardada = citaRepository.save(cita);
        return mapToResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CitaResponse obtenerPorId(Long id) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));
        return mapToResponse(cita);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listarTodas() {
        return citaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listarPorMascota(Long mascotaId) {
        if (!mascotaRepository.existsById(mascotaId)) {
            throw new ResourceNotFoundException("Mascota no encontrada con id: " + mascotaId);
        }
        return citaRepository.findByMascotaId(mascotaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listarPorVeterinario(Long veterinarioId) {
        if (!veterinarioRepository.existsById(veterinarioId)) {
            throw new ResourceNotFoundException("Veterinario no encontrado con id: " + veterinarioId);
        }
        return citaRepository.findByVeterinarioId(veterinarioId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CitaResponse> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return citaRepository.findByFechaHoraBetween(inicio, fin)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CitaResponse actualizar(Long id, CitaRequest request) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita no encontrada con id: " + id));

        Mascota mascota = mascotaRepository.findById(request.getMascotaId())
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + request.getMascotaId()));
        Veterinario veterinario = veterinarioRepository.findById(request.getVeterinarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Veterinario no encontrado con id: " + request.getVeterinarioId()));

        cita.setFechaHora(request.getFechaHora());
        cita.setMotivo(request.getMotivo());
        cita.setEstado(request.getEstado());
        cita.setMascota(mascota);
        cita.setVeterinario(veterinario);

        Cita actualizada = citaRepository.save(cita);
        return mapToResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!citaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cita no encontrada con id: " + id);
        }
        citaRepository.deleteById(id);
    }

    private CitaResponse mapToResponse(Cita cita) {
        CitaResponse response = new CitaResponse();
        response.setId(cita.getId());
        response.setFechaHora(cita.getFechaHora());
        response.setMotivo(cita.getMotivo());
        response.setEstado(cita.getEstado().name());
        response.setMascotaId(cita.getMascota().getId());
        response.setMascotaNombre(cita.getMascota().getNombre());
        response.setVeterinarioId(cita.getVeterinario().getId());
        response.setVeterinarioNombre(cita.getVeterinario().getNombre() + " " + cita.getVeterinario().getApellido());
        return response;
    }
}
