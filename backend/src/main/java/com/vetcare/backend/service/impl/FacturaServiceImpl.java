package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.FacturaRequest;
import com.vetcare.backend.dto.response.FacturaResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Cliente;
import com.vetcare.backend.model.Factura;
import com.vetcare.backend.repository.ClienteRepository;
import com.vetcare.backend.repository.FacturaRepository;
import com.vetcare.backend.service.FacturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public FacturaResponse crear(FacturaRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));

        Factura factura = new Factura();
        factura.setNumeroFactura(request.getNumeroFactura());
        factura.setFechaEmision(request.getFechaEmision());
        factura.setSubtotal(request.getSubtotal());
        factura.setImpuesto(request.getImpuesto());
        factura.setTotal(request.getTotal());
        factura.setCliente(cliente);
        // Los detalles se añadirían en un servicio separado, aquí solo factura básica.

        Factura guardada = facturaRepository.save(factura);
        return mapToResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public FacturaResponse obtenerPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada con id: " + id));
        return mapToResponse(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaResponse> listarTodas() {
        return facturaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FacturaResponse> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId);
        }
        return facturaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Factura no encontrada con id: " + id);
        }
        facturaRepository.deleteById(id);
    }

    private FacturaResponse mapToResponse(Factura factura) {
        FacturaResponse response = new FacturaResponse();
        response.setId(factura.getId());
        response.setNumeroFactura(factura.getNumeroFactura());
        response.setFechaEmision(factura.getFechaEmision());
        response.setSubtotal(factura.getSubtotal());
        response.setImpuesto(factura.getImpuesto());
        response.setTotal(factura.getTotal());
        response.setClienteId(factura.getCliente().getId());
        response.setClienteNombre(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
        return response;
    }
}
