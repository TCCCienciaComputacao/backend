package com.tcc.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.tcc.backend.entity.Materias;
import com.tcc.backend.service.MateriasService;

@RestController
@RequestMapping("api/materias")
@CrossOrigin(origins = "*") 
public class MateriasController {
    
    @Autowired
    private MateriasService materiasService; 

    @GetMapping("/")
     public List<Materias> buscarTodos(){
        return materiasService.buscarTodasMaterias();
    }

     @PostMapping("/")
    public Materias inserir( @RequestBody Materias materias){
        return materiasService.salvarMateria(materias);
    }

    @PutMapping("/")
    public Materias alterar(@RequestBody Materias materias){
        return materiasService.alterar(materias);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        materiasService.excluir(id);
        return ResponseEntity.ok().build();
    } 

}
