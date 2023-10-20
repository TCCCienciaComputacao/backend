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
import com.tcc.backend.entity.Periodos;
import com.tcc.backend.service.PeriodosService;

@RestController
@RequestMapping("api/periodos")
@CrossOrigin(origins = "*") 
public class PeriodosController {
    
    @Autowired
    private PeriodosService periodosService;


     @GetMapping("/")
    public List<Periodos> buscarTodos(){
        return periodosService.buscarTodos();
    }

    @PostMapping("/")
    public Periodos inserir( @RequestBody Periodos periodos){
        return periodosService.inserir(periodos);
    }

    @PutMapping("/")
    public Periodos alterar(@RequestBody Periodos periodos){
        return periodosService.alterar(periodos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
        periodosService.excluir(id);
        return ResponseEntity.ok().build();
    } 
}
