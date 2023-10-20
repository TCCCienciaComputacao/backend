package com.tcc.backend.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data

public class Usuarios {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "nome", nullable = false)
    private String nome;

    
    @Column(name = "email", nullable = false)
    private String email; 

    @Column(name = "senha", nullable = false)
    private String senha; 

    @Column(name = "tipousuario")
    private String tipousuario;

    @ManyToOne
    @JoinColumn(name = "enderecoid", referencedColumnName = "id")
    private Enderecos enderecos;

    @Column(name = "celular", nullable = false)
    private String celular;

    @Temporal(TemporalType.DATE)
    @Column(name = "datanascimento", nullable = false)
    private Date datanascimento;

    @Column(name = "genero")
    private String genero;

}
