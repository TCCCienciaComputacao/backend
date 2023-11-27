package com.tcc.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.backend.dto.TurmasProfessoresDTO;
import com.tcc.backend.dto.TurmasProfessoresProjection;
import com.tcc.backend.dto.TurmasProfessoresRequest;
import com.tcc.backend.entity.TurmasProfessores;
import com.tcc.backend.service.TurmasProfessoresService;

@RestController
@RequestMapping("api/turmasprofessores")
@CrossOrigin(origins = "*")
public class TurmasProfessoresController {

    @Autowired
    TurmasProfessoresService turmasProfessoresService;

    public TurmasProfessoresController(TurmasProfessoresService turmasProfessoresService) {
        this.turmasProfessoresService = turmasProfessoresService;
    }

    @PostMapping("/{turmaId}/{professorId}")
    public ResponseEntity<TurmasProfessores> inserir(@RequestBody TurmasProfessoresRequest turmasProfessoresRequest,
            @PathVariable Long turmaId, @PathVariable Long professorId) {
        try {
            TurmasProfessores turmasProfessores = turmasProfessoresService.inserir(turmasProfessoresRequest, turmaId,
                    professorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(turmasProfessores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmasProfessores> alterarTurmasProfessores(
            @PathVariable Long id,
            @RequestBody TurmasProfessoresDTO requestDTO) {
        try {
            Long turmaId = requestDTO.getTurmasId();
            Long professorId = requestDTO.getProfessoresId();

            TurmasProfessores turmasProfessoresAtualizado = turmasProfessoresService.alterar(id, requestDTO, turmaId,
                    professorId);
            return ResponseEntity.ok(turmasProfessoresAtualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<TurmasProfessores>> buscarTodos() {
        List<TurmasProfessores> turmasProfessoresList = turmasProfessoresService.buscarTodos();
        return ResponseEntity.ok(turmasProfessoresList);
    }

    @GetMapping("/professores")
    public ResponseEntity<List<TurmasProfessoresProjection>> getTurmasAndProfessores() {
        List<TurmasProfessoresProjection> turmasProfessores = turmasProfessoresService.getTurmasAndProfessores();
        return ResponseEntity.ok(turmasProfessores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) throws Exception {
        turmasProfessoresService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
