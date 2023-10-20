package com.tcc.backend.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "Professores")
@Data
public class Professores {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "id", nullable = false)
    private Usuarios usuario;

    @Column(name = "formacaoacademica")
    private String formacaoacademica;

    @Temporal(TemporalType.DATE)
    @Column(name = "datacontratacao")
    private Date datacontratacao;

    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "professores")
    private List<Disponibilidade> disponibilidades;


    public Professores orElse(Object object) {
        return null;
    }


    public boolean isPresent() {
        return false;
    }

}
