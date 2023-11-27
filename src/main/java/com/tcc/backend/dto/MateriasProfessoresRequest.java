package com.tcc.backend.dto;

import com.tcc.backend.entity.Materias;
import com.tcc.backend.entity.Professores;

import lombok.Data;

@Data
public class MateriasProfessoresRequest {
    private Professores professores; 
    private Materias materias; 
    private Long materiaId;
    private Long professorId;
}
