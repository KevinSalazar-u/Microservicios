package com.example.curso.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursoRequestDTO {

    private String nombre;
    private String modalidad;
    private Integer duracionMeses;
}