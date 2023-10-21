
package com.tcc.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(name = "diasemana")
    private int diasemana;

    private int horarioinicio;
    private int horariofim;

 

}

   
