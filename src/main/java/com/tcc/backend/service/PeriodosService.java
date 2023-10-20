package com.tcc.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcc.backend.entity.Periodos;

import com.tcc.backend.repository.PeriodosRepository;

@Service
public class PeriodosService {

    @Autowired
    private PeriodosRepository periodosRepository; 

    public List<Periodos> buscarTodos(){
        return periodosRepository.findAll();
    }

    public Periodos encontrarPeriodoPorId(Long id) {
        return periodosRepository.findById(id)
            .orElse(null); // Retorna null se o período não for encontrado
    }

    public Periodos inserir (Periodos periodos){
        Periodos novoPeriodo = periodosRepository.saveAndFlush(periodos);
        return novoPeriodo;
    }

    public Periodos alterar (Periodos periodos){
        return periodosRepository.saveAndFlush(periodos);
    }



      public void excluir(Long id){
        Periodos periodos = periodosRepository.findById(id).get();
        periodosRepository.delete(periodos);
    }


    
}
