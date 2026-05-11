package com.example.curso.services;

import com.example.curso.dto.CursoRequestDTO;
import com.example.curso.dto.CursoResponseDTO;

import java.util.List;

public interface CursoService {

    List<CursoResponseDTO> listar();

    CursoResponseDTO buscarPorId(Long id);

    CursoResponseDTO guardar(CursoRequestDTO cursoRequestDTO);

    CursoResponseDTO actualizar(Long id, CursoRequestDTO cursoRequestDTO);

    void eliminar(Long id);
}