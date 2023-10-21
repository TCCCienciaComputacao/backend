package com.tcc.backend.dto;

import lombok.Data;

@Data
public class Grade {
    private String professorName;
    private int periodo;
    private int hora;

    public Grade(String professorName, int periodo, int hora) {
        this.professorName = professorName;
        this.periodo = periodo;
        this.hora = hora;
    }

}
