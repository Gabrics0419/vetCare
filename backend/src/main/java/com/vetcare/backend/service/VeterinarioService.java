package com.vetcare.backend.service;

import com.vetcare.backend.dto.request.VeterinarioRequest;
import com.vetcare.backend.dto.response.VeterinarioResponse;

import java.util.List;

public interface VeterinarioService {

    VeterinarioResponse crear(VeterinarioRequest request);

    VeterinarioResponse obtenerPorId(Long id);

    List<VeterinarioResponse> listarTodos();

    VeterinarioResponse actualizar(Long id, VeterinarioRequest request);

    void eliminar(Long id);
}
