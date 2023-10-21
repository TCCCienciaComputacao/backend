package com.tcc.backend.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.backend.dto.Grade;
import com.tcc.backend.service.AulaService;

@Controller
@RestController
@RequestMapping("/aulas")
@CrossOrigin(origins = "*") 
public class AulaController {

    @Autowired
    private AulaService aulaService;
    
    @GetMapping("/{id}")
    public ResponseEntity<List<Grade>> obterAulas(@PathVariable Long id) {
        List<Grade> aulas = aulaService.gradeAula(id);
        return new ResponseEntity<>(aulas, HttpStatus.OK);
    }
}
