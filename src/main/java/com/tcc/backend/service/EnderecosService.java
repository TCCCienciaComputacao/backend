package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Enderecos;
import com.tcc.backend.repository.EnderecosRepository;

@Service
public class EnderecosService {
    
    @Autowired
    EnderecosRepository enderecosRepository;

    public List<Enderecos> buscarTodos(){
        return enderecosRepository.findAll();
    }

    public Enderecos inserir (Enderecos enderecos){
        Enderecos enderecosNovo = enderecosRepository.saveAndFlush(enderecos);
        return enderecosNovo;
    }

    public Enderecos alterar(Enderecos enderecos){
        return enderecosRepository.saveAndFlush(enderecos);
    }

    public void excluir(Long id){
        Enderecos enderecos = enderecosRepository.findById(id).get();
        enderecosRepository.delete(enderecos);
    }

}
