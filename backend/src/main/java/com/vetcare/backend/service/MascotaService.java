package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.MascotaRequest;
import com.vetcare.backend.dto.response.MascotaResponse;

import java.util.List;

public interface MascotaService {

    MascotaResponse crear(MascotaRequest request);

    MascotaResponse obtenerPorId(Long id);

    List<MascotaResponse> listarTodas();

    List<MascotaResponse> listarPorCliente(Long clienteId);

    MascotaResponse actualizar(Long id, MascotaRequest request);

    void eliminar(Long id);
}
