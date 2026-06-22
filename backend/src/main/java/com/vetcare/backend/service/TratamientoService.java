package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.TratamientoRequest;
import com.vetcare.backend.dto.response.TratamientoResponse;

import java.util.List;

public interface TratamientoService {

    TratamientoResponse crear(TratamientoRequest request);

    TratamientoResponse obtenerPorId(Long id);

    List<TratamientoResponse> listarTodos();

    List<TratamientoResponse> listarPorMascota(Long mascotaId);

    List<TratamientoResponse> listarPorVeterinario(Long veterinarioId);

    TratamientoResponse actualizar(Long id, TratamientoRequest request);

    void eliminar(Long id);
}
