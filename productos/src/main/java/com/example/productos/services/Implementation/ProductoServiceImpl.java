package com.example.productos.services.Implementation;

import com.example.productos.entities.Producto;
import com.example.productos.exceptions.RecursoNoEncontradoException;
import com.example.productos.repositories.ProductoRepository;
import com.example.productos.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public Page<Producto> findAll(Pageable pageable) {
        return productoRepository.findAll(pageable);
    }

    @Override
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un cliente con id: " + id));
    }

    @Override
    public Page<Producto> findByNombreOrCodigoOrMarca(String valor, Pageable pageable) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe enviar un nombre, email o telefono para buscar.");
        }
        return productoRepository.findByNombreContainingIgnoreCaseOrCodigoContainingIgnoreCaseOrMarcaContainingIgnoreCase(valor.trim(), valor.trim(), valor.trim(), pageable);
    }

    @Override
    public Producto save(Producto entity) {
        validarProducto(entity);
        if (productoRepository.existsByCodigoIgnoreCase(entity.getCodigo())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + entity.getCodigo());
        }
        return productoRepository.save(entity);
    }

    @Override
    public Producto update(Long id, Producto entity) {
        validarProducto(entity);
        Producto productoActual = findById(id);

        productoRepository.findByCodigoIgnoreCase(entity.getCodigo()).ifPresent(productoConCodigo -> {
            if (!productoConCodigo.getId().equals(id)) {
                throw new IllegalArgumentException("El codigo ya pertenece a otro producto: " + entity.getCodigo());
            }
        });

        productoActual.setNombre(entity.getNombre());
        productoActual.setDescripcion(entity.getDescripcion());
        productoActual.setPrecio(entity.getPrecio());
        productoActual.setStock(entity.getStock());
        productoActual.setCodigo(entity.getCodigo());
        productoActual.setMarca(entity.getMarca());

        return productoRepository.save(productoActual);
    }

    @Override
    public void deleteById(Long id) {
        Producto producto = findById(id);
        productoRepository.delete(producto);
    }

    private void validarProducto(Producto entity) {

        if (entity == null) {
            throw new IllegalArgumentException("El cuerpo de la solicitud no puede estar vacío.");
        }

        if (entity.getNombre() == null || entity.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        if (entity.getPrecio() == null) {
            throw new IllegalArgumentException("El precio es obligatorio.");
        }

        if (entity.getPrecio().doubleValue() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        }

        if (entity.getStock() == null) {
            throw new IllegalArgumentException("El stock es obligatorio.");
        }

        if (entity.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo.");
        }

        if (entity.getCodigo() == null || entity.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException("El código del producto es obligatorio.");
        }

        if (entity.getMarca() == null || entity.getMarca().trim().isEmpty()) {
            throw new IllegalArgumentException("La marca es obligatoria.");
        }
    }
}
