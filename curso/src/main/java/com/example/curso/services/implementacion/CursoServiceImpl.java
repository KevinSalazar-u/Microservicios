package com.example.curso.services.implementacion;

import com.example.curso.dto.CursoRequestDTO;
import com.example.curso.dto.CursoResponseDTO;
import com.example.curso.entities.Curso;
import com.example.curso.exceptions.RecursoNoEncontradoException;
import com.example.curso.repositories.CursoRepository;
import com.example.curso.services.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public List<CursoResponseDTO> listar() {
        return cursoRepository.findAll()
                .stream()
                .map(this::convertirAResponseDTO)
                .toList();
    }

    @Override
    public CursoResponseDTO buscarPorId(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));

        return convertirAResponseDTO(curso);
    }

    @Override
    public CursoResponseDTO guardar(CursoRequestDTO cursoRequestDTO) {
        Curso curso = Curso.builder()
                .nombre(cursoRequestDTO.getNombre())
                .modalidad(cursoRequestDTO.getModalidad())
                .duracionMeses(cursoRequestDTO.getDuracionMeses())
                .build();

        return convertirAResponseDTO(cursoRepository.save(curso));
    }

    @Override
    public CursoResponseDTO actualizar(Long id, CursoRequestDTO cursoRequestDTO) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));

        curso.setNombre(cursoRequestDTO.getNombre());
        curso.setModalidad(cursoRequestDTO.getModalidad());
        curso.setDuracionMeses(cursoRequestDTO.getDuracionMeses());

        return convertirAResponseDTO(cursoRepository.save(curso));
    }

    @Override
    public void eliminar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con id: " + id));

        cursoRepository.delete(curso);
    }

    private CursoResponseDTO convertirAResponseDTO(Curso curso) {
        return CursoResponseDTO.builder()
                .id(curso.getId())
                .nombre(curso.getNombre())
                .modalidad(curso.getModalidad())
                .duracionMeses(curso.getDuracionMeses())
                .build();
    }
}