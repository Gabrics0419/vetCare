package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.ClienteRequest;
import com.vetcare.backend.dto.response.ClienteResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Cliente;
import com.vetcare.backend.repository.ClienteRepository;
import com.vetcare.backend.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor  // Lombok genera constructor con todos los campos final
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public ClienteResponse crear(ClienteRequest request) {
        // Crear nueva entidad Cliente a partir del DTO de request
        Cliente cliente = new Cliente();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        // Guardar en base de datos
        Cliente guardado = clienteRepository.save(cliente);
        // Convertir a DTO de respuesta y retornar
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteResponse obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
        return mapToResponse(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteResponse> listarTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteResponse actualizar(Long id, ClienteRequest request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));

        // Actualizar campos
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());

        Cliente actualizado = clienteRepository.save(cliente);
        return mapToResponse(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    // Método privado para mapear Entidad -> DTO Response
    private ClienteResponse mapToResponse(Cliente cliente) {
        ClienteResponse response = new ClienteResponse();
        response.setId(cliente.getId());
        response.setNombre(cliente.getNombre());
        response.setApellido(cliente.getApellido());
        response.setEmail(cliente.getEmail());
        response.setTelefono(cliente.getTelefono());
        response.setDireccion(cliente.getDireccion());
        return response;
    }
}
