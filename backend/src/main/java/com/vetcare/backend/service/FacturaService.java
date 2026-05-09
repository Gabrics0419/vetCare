package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.FacturaRequest;
import com.vetcare.backend.dto.response.FacturaResponse;

import java.util.List;

public interface FacturaService {

    FacturaResponse crear(FacturaRequest request);

    FacturaResponse obtenerPorId(Long id);

    List<FacturaResponse> listarTodas();

    List<FacturaResponse> listarPorCliente(Long clienteId);

    void eliminar(Long id);
}
