package com.tcc.backend.dto;

import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.Turmas;

import lombok.Data;


@Data
public class TurmasProfessoresRequest {
    
    private Professores professores; 
    private Turmas turmas; 
    private Long turmaId;
    private Long professorId;
}
