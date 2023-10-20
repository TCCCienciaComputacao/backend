package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.dto.ProfessoresRequest;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.repository.ProfessoresRepository;
import com.tcc.backend.repository.UsuariosRepository;

@Service
public class ProfessoresService {
    
    @Autowired
    ProfessoresRepository professoresRepository;

    @Autowired
    UsuariosRepository usuariosRepository;


    public List<Professores> buscarTodos(){
        return professoresRepository.findAll();
    }

    public Professores buscarPorId(Long id) {
        
        return professoresRepository.findById(id).orElse(null);
    }

    public Professores inserir (ProfessoresRequest professoresRequest, Long usuarioId){

        Usuarios usuario = usuariosRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
         
        }

        Professores professorNovo = professoresRepository.saveAndFlush(professoresRequest);
        return professorNovo;
    }

    public Professores alterar (Professores professores){
        return professoresRepository.saveAndFlush(professores);
    }

    public void excluir(Long id){
    Professores professores = professoresRepository.findById(id).get();
        professoresRepository.delete(professores);
    }

    public Professores inserir(Professores novoProfessor) {
        return null;
    }



}
