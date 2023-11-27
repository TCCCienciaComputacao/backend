package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Materias;
import com.tcc.backend.repository.MateriasRepository;

@Service
public class MateriasService {
    @Autowired
    private MateriasRepository materiasRepository; 


    public List<Materias> buscarTodasMaterias(){
        return materiasRepository.findAll();
    }
    public Materias salvarMateria(Materias materias){
        Materias novaMateria = materiasRepository.saveAndFlush(materias);
        return novaMateria;
    }

    public Materias alterar(Materias materias){
        return materiasRepository.saveAndFlush(materias);
    }

    public void excluir(long id){
        Materias materias = materiasRepository.findById(id).get();
        materiasRepository.delete(materias); 
    }


}
