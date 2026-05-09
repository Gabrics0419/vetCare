package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.CitaRequest;
import com.vetcare.backend.dto.response.CitaResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaService {

    CitaResponse crear(CitaRequest request);

    CitaResponse obtenerPorId(Long id);

    List<CitaResponse> listarTodas();

    List<CitaResponse> listarPorMascota(Long mascotaId);

    List<CitaResponse> listarPorVeterinario(Long veterinarioId);

    List<CitaResponse> listarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin);

    CitaResponse actualizar(Long id, CitaRequest request);

    void eliminar(Long id);
}
