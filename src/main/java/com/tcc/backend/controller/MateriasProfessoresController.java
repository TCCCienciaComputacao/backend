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
import com.tcc.backend.dto.MateriasProfessoresProjection;
import com.tcc.backend.dto.MateriasProfessoresRequest;
import com.tcc.backend.entity.MateriasProfessores;
import com.tcc.backend.service.MateriasProfessoresService;

@RestController
@RequestMapping("api/materiasprofessores")
@CrossOrigin(origins = "*")
public class MateriasProfessoresController {
    
    @Autowired
    MateriasProfessoresService materiasProfessoresService;


     public MateriasProfessoresController(MateriasProfessoresService materiasProfessoresService) {
        this.materiasProfessoresService = materiasProfessoresService;
    }

    @PostMapping("/{materiaId}/{professorId}")
    public ResponseEntity<MateriasProfessores> inserir(@RequestBody MateriasProfessoresRequest materiasProfessoresRequest,
            @PathVariable Long materiaId, @PathVariable Long professorId) {
        try {
            MateriasProfessores materiasProfessores = materiasProfessoresService.inserir(materiasProfessoresRequest, materiaId,
                    professorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(materiasProfessores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriasProfessores> alterarMateriaProfessores(
            @PathVariable Long id,
            @RequestBody MateriasProfessoresRequest materiasProfessoresRequest) {
        try {
            Long materiaId = materiasProfessoresRequest.getMateriaId();
            Long professorId = materiasProfessoresRequest.getProfessorId();
            
            MateriasProfessores materiaProfessoresAtualizados = materiasProfessoresService.alterar(id, materiasProfessoresRequest, materiaId, professorId);
            return ResponseEntity.ok(materiaProfessoresAtualizados);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<MateriasProfessores>> buscarTodos() {
        List<MateriasProfessores> materiasProfessoresList = materiasProfessoresService.buscarTodos();
        return ResponseEntity.ok(materiasProfessoresList);
    }

    @GetMapping("/professores")
    public ResponseEntity<List<MateriasProfessoresProjection>> getMateriasAndProfessores() {
        List<MateriasProfessoresProjection> materiasProfessores = materiasProfessoresService.getMateriassAndProfessores();
        return ResponseEntity.ok(materiasProfessores);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) throws Exception {
        materiasProfessoresService.excluir(id);
        return ResponseEntity.noContent().build();
    }

}
