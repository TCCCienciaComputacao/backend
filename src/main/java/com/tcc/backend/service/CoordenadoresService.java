package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Coordenadores;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.repository.CoordenadoresRepository;
import com.tcc.backend.repository.UsuariosRepository;

@Service
public class CoordenadoresService {
    
    @Autowired
    private CoordenadoresRepository coordenadoresRepository;

    @Autowired
    private UsuariosRepository usuariosRepository;

    public List<Coordenadores> buscarTodos(){
        return coordenadoresRepository.findAll();
    }


    public Coordenadores buscarPorId(Long id) {
    return coordenadoresRepository.findById(id).orElse(null);
    }

    public Coordenadores inserir (Coordenadores coordenadores, Long usuarioId){
        Usuarios usuario = usuariosRepository.findById(usuarioId).orElse(null);
        if (usuario == null) {
         
        }

        Coordenadores coordenadorNovo = coordenadoresRepository.saveAndFlush(coordenadores);
        return coordenadorNovo;
    }


    
    public Coordenadores alterar (Coordenadores coordenadores){
        return coordenadoresRepository.saveAndFlush(coordenadores);
    }

    public void excluir(Long id){
        Coordenadores coordenadores = coordenadoresRepository.findById(id).get();
        coordenadoresRepository.delete(coordenadores);
    }

}
