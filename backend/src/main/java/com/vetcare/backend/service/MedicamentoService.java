package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.MedicamentoRequest;
import com.vetcare.backend.dto.response.MedicamentoResponse;

import java.util.List;

public interface MedicamentoService {

    MedicamentoResponse crear(MedicamentoRequest request);

    MedicamentoResponse obtenerPorId(Long id);

    List<MedicamentoResponse> listarTodos();

    MedicamentoResponse actualizar(Long id, MedicamentoRequest request);

    void eliminar(Long id);
}
