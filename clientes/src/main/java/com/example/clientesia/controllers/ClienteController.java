package com.example.clientesia.controllers;

import com.example.clientesia.entities.Cliente;
import com.example.clientesia.services.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listar(Pageable pageable) {
        Page<Cliente> page = clienteService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("clientes", page.getContent());
        response.put("total", page.getTotalElements());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarCliente(
            @RequestParam String valor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        Page<Cliente> pageClientes = clienteService.findByNombreOrEmailOrTelefono(valor, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("clientes", pageClientes.getContent());
        response.put("total", pageClientes.getTotalElements());
        response.put("totalPaginas", pageClientes.getTotalPages());
        response.put("paginaActual", pageClientes.getNumber());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable Long id, @RequestBody Cliente entity) {
        return ResponseEntity.ok(clienteService.update(id, entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
