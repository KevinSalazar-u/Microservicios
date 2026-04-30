package com.example.productos.services;

import com.example.productos.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoService {
    Page<Producto> findAll(Pageable pageable);
    Producto findById(Long id);
    Page<Producto> findByNombreOrCodigoOrMarca(String valor, Pageable pageable);
    Producto save(Producto entity);
    Producto update(Long id, Producto entity);
    void deleteById(Long id);
}
