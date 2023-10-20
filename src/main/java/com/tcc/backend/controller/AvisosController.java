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

import com.tcc.backend.entity.Avisos;
import com.tcc.backend.service.AvisosService;

@RequestMapping("api/avisos")
@RestController
@CrossOrigin(origins = "*") 

public class AvisosController {
    
    @Autowired
    private AvisosService avisosService;

    @PostMapping("/")
    public ResponseEntity<Object> criarTurma(@RequestBody Avisos avisos) {
        avisosService.salvarAviso(avisos); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Aviso cadastrado com sucesso.");
    }


    @GetMapping("/")
    public List<Avisos> listarTurmas() {
        return avisosService.listarAvisos();
    }


    @GetMapping("/{id}")
    public Optional<Avisos> encontrarTurmaPorId(@PathVariable Long id) {
        return avisosService.encontrarAvisosPorId(id);
    }

     @PutMapping("/")
    public Avisos alterar(@RequestBody Avisos avisos){
        return avisosService.alterar(avisos);
    }

    @DeleteMapping("/{id}")
    public void excluirTurma(@PathVariable Long id) {
        avisosService.excluirAviso(id);
    }
}
