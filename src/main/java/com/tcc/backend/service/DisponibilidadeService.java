package com.tcc.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.repository.DisponibilidadeRepository;

public class DisponibilidadeService {
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public Disponibilidade inserir(Disponibilidade disponibilidade) {
        return disponibilidadeRepository.save(disponibilidade);
    }
}
