package com.example.curso.controllers;

import com.example.curso.dto.CursoRequestDTO;
import com.example.curso.dto.CursoResponseDTO;
import com.example.curso.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<CursoResponseDTO> listar() {
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public CursoResponseDTO buscarPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @PostMapping
    public CursoResponseDTO guardar(@RequestBody CursoRequestDTO cursoRequestDTO) {
        return cursoService.guardar(cursoRequestDTO);
    }

    @PutMapping("/{id}")
    public CursoResponseDTO actualizar(@PathVariable Long id, @RequestBody CursoRequestDTO cursoRequestDTO) {
        return cursoService.actualizar(id, cursoRequestDTO);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
        return "Curso eliminado correctamente";
    }
}