package com.tcc.backend.controller;

import java.util.List;
import java.util.Optional;

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
import com.tcc.backend.entity.Turmas;
import com.tcc.backend.service.TurmasService;

@RestController
@RequestMapping("api/turmas")
@CrossOrigin(origins = "*") 
public class TurmasController {
    

    @Autowired
    private TurmasService turmasService;

    @PostMapping("/")
    public ResponseEntity<Object> criarTurma(@RequestBody Turmas turma) {
        turmasService.salvarTurma(turma); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Turma cadastrada com sucesso.");
    }


    @GetMapping("/")
    public List<Turmas> listarTurmas() {
        return turmasService.listarTurmas();
    }


    @GetMapping("/{id}")
    public Optional<Turmas> encontrarTurmaPorId(@PathVariable Long id) {
        return turmasService.encontrarTurmaPorId(id);
    }

     @PutMapping("/")
    public Turmas alterar(@RequestBody Turmas turmas){
        return turmasService.alterar(turmas);
    }

    @DeleteMapping("/{id}")
    public void excluirTurma(@PathVariable Long id) {
        turmasService.excluirTurma(id);
    }
}
