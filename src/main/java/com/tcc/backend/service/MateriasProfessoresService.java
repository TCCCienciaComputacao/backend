package com.tcc.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.dto.MateriasProfessoresProjection;
import com.tcc.backend.dto.MateriasProfessoresRequest;
import com.tcc.backend.entity.Materias;
import com.tcc.backend.entity.MateriasProfessores;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.repository.MateriasProfessoresRepository;
import com.tcc.backend.repository.MateriasRepository;
import com.tcc.backend.repository.ProfessoresRepository;

@Service
public class MateriasProfessoresService {
    
    @Autowired
    MateriasProfessoresRepository materiasProfessoresRepository; 

    @Autowired
    MateriasRepository materiasRepository; 

     @Autowired
    ProfessoresRepository professoresRepository;

       public MateriasProfessores inserir(MateriasProfessoresRequest materiasProfessoresRequest, Long materiaId, Long professorId)
            throws Exception {
        if (materiaId == null || professorId == null) {
            throw new Exception("Erro ao encontrar materia ou professor");
        }

        MateriasProfessores materiasProfessoresNovo = new MateriasProfessores();
        // Obtenha as instâncias de Turmas e Professores com base nos IDs
        Materias materia = materiasRepository.findById(materiaId).orElseThrow(() -> new Exception("Turma não encontrada"));
        Professores professor = professoresRepository.findById(professorId)
                .orElseThrow(() -> new Exception("Professor não encontrado"));

        // Defina as associações corretas
        materiasProfessoresNovo.setMaterias(materia);
        materiasProfessoresNovo.setProfessores(professor);

        // Salve a nova associação no banco de dados
        return materiasProfessoresRepository.save(materiasProfessoresNovo);
    }

    public MateriasProfessores alterar(Long id, MateriasProfessoresRequest materiasProfessoresRequest, Long materiaId, Long professorId) throws Exception {
        if (materiaId == null || professorId == null) {
            throw new Exception("Erro ao encontrar turma ou professor");
        }
    
        // Verifica se a associação entre a turma e o professor já existe
        MateriasProfessores materiaProfessorExistente = materiasProfessoresRepository.findByMateriaIdAndProfessorId(materiaId, professorId);
    
        if (materiaProfessorExistente == null) {
            throw new Exception("Associação não encontrada");
        }
    
        // Atualize a associação existente em vez de criar uma nova
        // Obtenha as instâncias de Turmas e Professores com base nos IDs
        Materias materia = materiasRepository.findById(materiaId).orElseThrow(() -> new Exception("Materia não encontrada"));
        Professores professor = professoresRepository.findById(professorId)
                .orElseThrow(() -> new Exception("Professor não encontrado"));
    
        // Atualize as associações corretas
        materiaProfessorExistente.setMaterias(materia);
        materiaProfessorExistente.setProfessores(professor);
    
        // Salve a associação atualizada no banco de dados
        return materiasProfessoresRepository.save(materiaProfessorExistente);
    }


     public List<MateriasProfessores> buscarTodos() {
        return materiasProfessoresRepository.findAll();
    }

    public void excluir(Long id) throws Exception {
        Optional<MateriasProfessores> turmasProfessoresOptional = materiasProfessoresRepository.findById(id);

        if (turmasProfessoresOptional.isPresent()) {
            materiasProfessoresRepository.delete(turmasProfessoresOptional.get());
        } else {
            throw new Exception("Associação não encontrada");
        }
    }

    public List<MateriasProfessoresProjection> getMateriassAndProfessores() {
        return materiasProfessoresRepository.getMateriasAndProfessores();
    }


}
