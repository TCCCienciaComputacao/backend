package com.tcc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tcc.backend.entity.Periodos;
import com.tcc.backend.entity.Turmas;
import com.tcc.backend.repository.PeriodosRepository;
import com.tcc.backend.repository.TurmasRepository;

@Service
public class TurmasService {
    
    @Autowired
    private TurmasRepository turmasRepository; 

    @Autowired
    private PeriodosRepository periodosRepository;


    public Turmas salvarTurma(Turmas turmas) {

        if (turmas.getPeriodos() != null && turmas.getId() != null) {
            Periodos periodo = periodosRepository.findById(turmas.getPeriodos().getId()).orElse(null);
            if (periodo != null) {
                turmas.setPeriodos(periodo);
            }
        }

        return turmasRepository.save(turmas);
    }

    public List<Turmas> listarTurmas() {
        return turmasRepository.findAll();
    }

    public Turmas alterar(Turmas turmas){

        if (turmas.getPeriodos() != null && turmas.getId() != null) {
            Periodos periodo = periodosRepository.findById(turmas.getPeriodos().getId()).orElse(null);
            if (periodo != null) {
                turmas.setPeriodos(periodo);
            }
        }

        return turmasRepository.saveAndFlush(turmas);
    }

    public Optional<Turmas> encontrarTurmaPorId(Long id) {
        return turmasRepository.findById(id);
    }

    public void excluirTurma(Long id) {
        turmasRepository.deleteById(id);
    }

}
