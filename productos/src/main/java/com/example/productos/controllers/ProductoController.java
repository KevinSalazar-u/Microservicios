package com.example.productos.controllers;

import com.example.productos.entities.Producto;
import com.example.productos.services.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        Page<Producto> productosPage = productoService.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("productos", productosPage.getContent());
        response.put("total", productosPage.getTotalElements());
        response.put("totalPaginas", productosPage.getTotalPages());
        response.put("paginaActual", productosPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<Map<String, Object>> buscarProductos(
            @RequestParam String valor,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());

        Page<Producto> productosPage = productoService.findByNombreOrCodigoOrMarca(valor, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("productos", productosPage.getContent());
        response.put("total", productosPage.getTotalElements());
        response.put("totalPaginas", productosPage.getTotalPages());
        response.put("paginaActual", productosPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.save(producto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable Long id,
            @RequestBody Producto producto
    ) {
        return ResponseEntity.ok(productoService.update(id, producto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> eliminarProducto(@PathVariable Long id) {
        productoService.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Producto eliminado correctamente");

        return ResponseEntity.ok(response);
    }
}
