package com.example.clientesia.services.implementacion;

import com.example.clientesia.entities.Cliente;
import com.example.clientesia.exceptions.RecursoNoEncontradoException;
import com.example.clientesia.repositories.ClienteRepository;
import com.example.clientesia.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un cliente con id: " + id));
    }

    @Override
    public Page<Cliente> findByNombreOrEmailOrTelefono(String valor, Pageable pageable) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar un nombre, email o telefono para buscar.");
        }
        return clienteRepository.findByNombreContainingIgnoreCaseOrEmailContainingIgnoreCaseOrTelefonoContainingIgnoreCase(valor.trim(), valor.trim(), valor.trim(), pageable);
    }

    @Override
    public Cliente save(Cliente entity) {
        validarCliente(entity);
        if (clienteRepository.existsByEmailIgnoreCase(entity.getEmail())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + entity.getEmail());
        }
        return clienteRepository.save(entity);
    }

    @Override
    public Cliente update(Long id, Cliente entity) {
        validarCliente(entity);
        Cliente clienteActual = findById(id);

        clienteRepository.findByEmailIgnoreCase(entity.getEmail()).ifPresent(clienteConEmail -> {
            if (!clienteConEmail.getId().equals(id)) {
                throw new IllegalArgumentException("El email ya pertenece a otro cliente: " + entity.getEmail());
            }
        });

        clienteActual.setNombre(entity.getNombre());
        clienteActual.setEmail(entity.getEmail());
        clienteActual.setTelefono(entity.getTelefono());
        clienteActual.setDireccion(entity.getDireccion());
        clienteActual.setActivo(entity.getActivo() == null ? clienteActual.getActivo() : entity.getActivo());

        return clienteRepository.save(clienteActual);
    }

    @Override
    public void delete(Long id) {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    private void validarCliente(Cliente entity) {
        if (entity == null) {
            throw new IllegalArgumentException("El cuerpo de la solicitud no puede estar vacío.");
        }
        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (entity.getEmail() == null || entity.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (!entity.getEmail().contains("@")) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
    }
}
