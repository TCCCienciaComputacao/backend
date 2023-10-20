package com.tcc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Avisos;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.repository.AvisosRepository;
import com.tcc.backend.repository.ProfessoresRepository;

@Service
public class AvisosService {
    
    @Autowired
    private AvisosRepository avisosRepository;

    @Autowired
    private ProfessoresRepository professoresRepository;

    public Avisos salvarAviso(Avisos avisos){
        if(avisos.getProfessores() != null && avisos.getId() != null){
            Professores professores = professoresRepository.findById(avisos.getProfessores().getId()).orElse(null);
            if(professores != null){
                avisos.setProfessores(professores);
            }


        }

        return avisosRepository.save(avisos);
    }

    public List<Avisos> listarAvisos() {
        return avisosRepository.findAll();
    }

    
    public Avisos alterar(Avisos avisos){

         if(avisos.getProfessores() != null && avisos.getId() != null){
            Professores professores = professoresRepository.findById(avisos.getProfessores().getId()).orElse(null);
            if(professores != null){
                avisos.setProfessores(professores);
            }


        }

        return avisosRepository.save(avisos);
    }


        public Optional<Avisos> encontrarAvisosPorId(Long id) {
        return avisosRepository.findById(id);
    }

    public void excluirAviso(Long id) {
        avisosRepository.deleteById(id);
    }



}
