package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.repository.DisponibilidadeRepository;
import com.tcc.backend.repository.ProfessoresRepository;

@Service
public class DisponibilidadeService {
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;
    
    public List<Disponibilidade> buscarTodos(){
        return disponibilidadeRepository.findAll();
    }
    
    public Disponibilidade inserir(Disponibilidade disponibilidades){

        if (disponibilidades.getProfessores() != null && disponibilidades.getProfessores().getId() != null) {
            Professores professor = professoresRepository.findById(disponibilidades.getProfessores().getId()).orElse(null);
            if (professor != null) {
                disponibilidades.setProfessores(professor);
            }
        }

        Disponibilidade disponibilidadeNova = disponibilidadeRepository.saveAndFlush(disponibilidades);
        return disponibilidadeNova;
        
    }

    public Disponibilidade alterar(Disponibilidade disponibilidades){

          if (disponibilidades.getProfessores() != null && disponibilidades.getProfessores().getId() != null) {
            Professores professor = professoresRepository.findById(disponibilidades.getProfessores().getId()).orElse(null);
            if (professor != null) {
                disponibilidades.setProfessores(professor);
            }
        }
        return disponibilidadeRepository.saveAndFlush(disponibilidades);
    }

    public void excluir(Long id){
        Disponibilidade disponibilidade = disponibilidadeRepository.findById(id).get();
        disponibilidadeRepository.delete(disponibilidade);
    }


}
