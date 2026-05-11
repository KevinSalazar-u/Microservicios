package com.example.curso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoResponseDTO {

    private Long id;
    private String nombre;
    private String modalidad;
    private Integer duracionMeses;
}