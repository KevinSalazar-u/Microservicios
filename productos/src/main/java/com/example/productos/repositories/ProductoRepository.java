package com.example.productos.repositories;

import com.example.productos.entities.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoIgnoreCase(String codigo);
    boolean existsByCodigoIgnoreCase(String codigo);
    Page<Producto> findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrMarcaContainingIgnoreCase(String nombre, String codigo, String marca, Pageable pageable);

}
