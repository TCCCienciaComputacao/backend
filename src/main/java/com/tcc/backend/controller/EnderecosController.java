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
import com.tcc.backend.entity.Enderecos;
import com.tcc.backend.service.EnderecosService;

@RestController
@RequestMapping("/api/enderecos")
@CrossOrigin(origins = "*") 
public class EnderecosController {

    @Autowired
    private EnderecosService enderecosService;

    @GetMapping("/")
    public List<Enderecos> buscarTodos(){
        return enderecosService.buscarTodos();
    }

    @PostMapping("/")
    public Enderecos inserir( @RequestBody Enderecos enderecos){
        return enderecosService.inserir(enderecos);
    }

    @PutMapping("/")
    public Enderecos alterar(@RequestBody Enderecos enderecos){
        return enderecosService.alterar(enderecos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        enderecosService.excluir(id);
        return ResponseEntity.ok().build();
    } 
}
