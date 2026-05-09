package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.ClienteRequest;
import com.vetcare.backend.dto.response.ClienteResponse;

import java.util.List;

public interface ClienteService {

    ClienteResponse crear(ClienteRequest request);

    ClienteResponse obtenerPorId(Long id);

    List<ClienteResponse> listarTodos();

    ClienteResponse actualizar(Long id, ClienteRequest request);

    void eliminar(Long id);
}
