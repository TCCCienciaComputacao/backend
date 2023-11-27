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
import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.service.DisponibilidadeService;

@RequestMapping("api/disponibilidade")
@RestController
@CrossOrigin(origins = "*") 
public class DisponibilidadeController {
    
   @Autowired
    private DisponibilidadeService disponibilidadeService;
    
    @PostMapping("/")
    public ResponseEntity<Object> inserir(@RequestBody Disponibilidade disponibilidades) {
    
        disponibilidadeService.inserir(disponibilidades); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro efetuado com sucesso.");
    }

     @GetMapping("/")
    public List<Disponibilidade> buscarTodos(){
        return disponibilidadeService.buscarTodos();
    }

      @PutMapping("/")
    public Disponibilidade alterar(@RequestBody Disponibilidade disponibilidade){
        return disponibilidadeService.alterar(disponibilidade);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
         disponibilidadeService.excluir(id);
         return ResponseEntity.ok().build();
    }


}
