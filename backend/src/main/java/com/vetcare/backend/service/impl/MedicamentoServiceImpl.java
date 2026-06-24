package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.MedicamentoRequest;
import com.vetcare.backend.dto.response.MedicamentoResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Medicamento;
import com.vetcare.backend.repository.MedicamentoRepository;
import com.vetcare.backend.service.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicamentoServiceImpl implements MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    @Override
    @Transactional
    public MedicamentoResponse crear(MedicamentoRequest request) {
        Medicamento medicamento = new Medicamento();
        medicamento.setNombre(request.getNombre());
        medicamento.setDescripcion(request.getDescripcion());
        medicamento.setStock(request.getStock());
        medicamento.setPrecioVenta(request.getPrecioVenta());

        Medicamento guardado = medicamentoRepository.save(medicamento);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public MedicamentoResponse obtenerPorId(Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));
        return mapToResponse(medicamento);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicamentoResponse> listarTodos() {
        return medicamentoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MedicamentoResponse actualizar(Long id, MedicamentoRequest request) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicamento no encontrado con id: " + id));

        medicamento.setNombre(request.getNombre());
        medicamento.setDescripcion(request.getDescripcion());
        medicamento.setStock(request.getStock());
        medicamento.setPrecioVenta(request.getPrecioVenta());

        Medicamento actualizado = medicamentoRepository.save(medicamento);
        return mapToResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Medicamento no encontrado con id: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

    private MedicamentoResponse mapToResponse(Medicamento medicamento) {
        MedicamentoResponse response = new MedicamentoResponse();
        response.setId(medicamento.getId());
        response.setNombre(medicamento.getNombre());
        response.setDescripcion(medicamento.getDescripcion());
        response.setStock(medicamento.getStock());
        response.setPrecioVenta(medicamento.getPrecioVenta());
        return response;
    }
}
