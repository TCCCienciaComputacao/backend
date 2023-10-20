package com.tcc.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.backend.entity.Aula;
import com.tcc.backend.service.AulaService;

@Controller
@RestController
@RequestMapping("/aulas")
@CrossOrigin(origins = "*") 
public class AulaController {

    @Autowired
    private AulaService aulaService;
    
    @GetMapping("/{nometurma}")
    public ResponseEntity<Map<String, Object>> obterOrganizacaoAulasParaTurmaNaSemana(@PathVariable String nometurma) {
        try {
            List<Aula> organizacaoAulas = aulaService.organizarAulasParaTurmaNaSemana(nometurma);
    
            Map<String, Object> response = new HashMap<>();
            response.put("aulasOrganizadas", organizacaoAulas);
    
            List<Aula> aulasComConflito = aulaService.getAulasComConflito(organizacaoAulas);
            response.put("aulasComConflito", aulasComConflito);
    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Ocorreu um erro ao obter as aulas.");
            return ResponseEntity.status(500).body(errorResponse);
        }
    }
}
