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
@Table(name = "turmas")
@Data
public class Turmas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nometurma")
    private String nometurma;

    @Column(name = "nivelano")
    private String nivelano;

    @ManyToOne
    @JoinColumn(name = "periodoid", referencedColumnName = "id", nullable = false)
    private Periodos periodos;


    
}
