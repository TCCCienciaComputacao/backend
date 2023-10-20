
package com.tcc.backend.entity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "aula")

public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @ManyToOne
    @JoinColumn(name = "professoresid", referencedColumnName = "id")
    private Professores professores;

    @ManyToOne
    @JoinColumn(name = "turmaid", referencedColumnName = "id")
    private Turmas turmas;

    @Enumerated(EnumType.STRING) // Use EnumType.STRING para mapear enums como strings
    @Column(name = "diasemana")
    private DayOfWeek diasemana;

    private LocalTime horarioinicio;
    private LocalTime horariofim;

 

}

   
