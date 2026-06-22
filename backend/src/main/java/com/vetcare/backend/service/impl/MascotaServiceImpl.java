package com.vetcare.backend.service.impl;

import com.vetcare.backend.dto.request.MascotaRequest;
import com.vetcare.backend.dto.response.MascotaResponse;
import com.vetcare.backend.exception.ResourceNotFoundException;
import com.vetcare.backend.model.Cliente;
import com.vetcare.backend.model.Mascota;
import com.vetcare.backend.repository.ClienteRepository;
import com.vetcare.backend.repository.MascotaRepository;
import com.vetcare.backend.service.MascotaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MascotaServiceImpl implements MascotaService {

    private final MascotaRepository mascotaRepository;
    private final ClienteRepository clienteRepository;

    @Override
    @Transactional
    public MascotaResponse crear(MascotaRequest request) {
        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));

        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setCliente(cliente);

        Mascota guardada = mascotaRepository.save(mascota);
        return mapToResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public MascotaResponse obtenerPorId(Long id) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + id));
        return mapToResponse(mascota);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponse> listarTodas() {
        return mascotaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MascotaResponse> listarPorCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new ResourceNotFoundException("Cliente no encontrado con id: " + clienteId);
        }
        return mascotaRepository.findByClienteId(clienteId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MascotaResponse actualizar(Long id, MascotaRequest request) {
        Mascota mascota = mascotaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mascota no encontrada con id: " + id));

        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + request.getClienteId()));

        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setRaza(request.getRaza());
        mascota.setFechaNacimiento(request.getFechaNacimiento());
        mascota.setCliente(cliente);

        Mascota actualizada = mascotaRepository.save(mascota);
        return mapToResponse(actualizada);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!mascotaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Mascota no encontrada con id: " + id);
        }
        mascotaRepository.deleteById(id);
    }

    private MascotaResponse mapToResponse(Mascota mascota) {
        MascotaResponse response = new MascotaResponse();
        response.setId(mascota.getId());
        response.setNombre(mascota.getNombre());
        response.setEspecie(mascota.getEspecie());
        response.setRaza(mascota.getRaza());
        response.setFechaNacimiento(mascota.getFechaNacimiento());
        response.setClienteId(mascota.getCliente().getId());
        response.setClienteNombreCompleto(mascota.getCliente().getNombre() + " " + mascota.getCliente().getApellido());
        return response;
    }
}
