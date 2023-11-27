package com.tcc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.dto.TurmasProfessoresDTO;
import com.tcc.backend.dto.TurmasProfessoresProjection;
import com.tcc.backend.dto.TurmasProfessoresRequest;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.Turmas;
import com.tcc.backend.entity.TurmasProfessores;
import com.tcc.backend.repository.ProfessoresRepository;
import com.tcc.backend.repository.TurmasProfessoresRepository;
import com.tcc.backend.repository.TurmasRepository;

@Service
public class TurmasProfessoresService {

    @Autowired
    TurmasProfessoresRepository turmasProfessoresRepository;

    @Autowired
    TurmasRepository turmasRepository;

    @Autowired
    ProfessoresRepository professoresRepository;

    public TurmasProfessores inserir(TurmasProfessoresRequest turmasProfessoresRequest, Long turmaId, Long professorId)
            throws Exception {
        if (turmaId == null || professorId == null) {
            throw new Exception("Erro ao encontrar turma ou professor");
        }

        TurmasProfessores turmaProfessoresNovo = new TurmasProfessores();
        // Obtenha as instâncias de Turmas e Professores com base nos IDs
        Turmas turma = turmasRepository.findById(turmaId).orElseThrow(() -> new Exception("Turma não encontrada"));
        Professores professor = professoresRepository.findById(professorId)
                .orElseThrow(() -> new Exception("Professor não encontrado"));

        // Defina as associações corretas
        turmaProfessoresNovo.setTurmas(turma);
        turmaProfessoresNovo.setProfessores(professor);

        // Salve a nova associação no banco de dados
        return turmasProfessoresRepository.save(turmaProfessoresNovo);
    }

    public TurmasProfessores alterar(Long id, TurmasProfessoresDTO requestDTO, Long turmaId, Long professorId) throws Exception {
        if (turmaId == null || professorId == null) {
            throw new Exception("Erro ao encontrar turma ou professor");
        }
    
        // Verifica se a associação entre a turma e o professor já existe
        TurmasProfessores turmaProfessoresExistente = turmasProfessoresRepository.findByTurmaIdAndProfessorId(turmaId, professorId);
    
        if (turmaProfessoresExistente == null) {
            throw new Exception("Associação não encontrada");
        }
    
        // Atualize a associação existente em vez de criar uma nova
        // Obtenha as instâncias de Turmas e Professores com base nos IDs
        Turmas turma = turmasRepository.findById(turmaId).orElseThrow(() -> new Exception("Turma não encontrada"));
        Professores professor = professoresRepository.findById(professorId)
                .orElseThrow(() -> new Exception("Professor não encontrado"));
    
        // Atualize as associações corretas
        turmaProfessoresExistente.setTurmas(turma);
        turmaProfessoresExistente.setProfessores(professor);
    
        // Salve a associação atualizada no banco de dados
        return turmasProfessoresRepository.save(turmaProfessoresExistente);
    }

    public List<TurmasProfessores> buscarTodos() {
        return turmasProfessoresRepository.findAll();
    }

    public void excluir(Long id) throws Exception {
        Optional<TurmasProfessores> turmasProfessoresOptional = turmasProfessoresRepository.findById(id);

        if (turmasProfessoresOptional.isPresent()) {
            turmasProfessoresRepository.delete(turmasProfessoresOptional.get());
        } else {
            throw new Exception("Associação não encontrada");
        }
    }

    public List<TurmasProfessoresProjection> getTurmasAndProfessores() {
        return turmasProfessoresRepository.getTurmasAndProfessores();
    }
}
