package com.example.clientesia.services;

import com.example.clientesia.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteService {
    Page<Cliente> findAll(Pageable pageable);
    Cliente findById(Long id);
    Page<Cliente> findByNombreOrEmailOrTelefono(String valor, Pageable pageable);
    Cliente save(Cliente entity);
    Cliente update(Long id, Cliente entity);
    void delete(Long id);
}
