package com.tcc.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.backend.entity.Coordenadores;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.service.CoordenadoresService;
import com.tcc.backend.service.UsuariosService;

@RestController
@RequestMapping("api/coordenadores")
@CrossOrigin(origins = "*") 

public class CoordenadoresController {
    
    @Autowired
    private CoordenadoresService coordenadoresService;

    @Autowired
    private UsuariosService usuariosService;

    @PostMapping("/")
    public ResponseEntity<Object> inserir(@RequestBody Coordenadores coordenadores, @RequestParam Long usuarioId) {
    Usuarios usuario = usuariosService.buscarPorId(usuarioId);
    if (usuario == null) {
        String mensagemDeErro = "Usuário não encontrado";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
    }

    coordenadores.setUsuario(usuario);

    Coordenadores novoCoordenador = coordenadoresService.inserir(coordenadores,usuarioId);
    return ResponseEntity.ok(novoCoordenador);
}


@PutMapping("/{id}")
public ResponseEntity<Coordenadores> alterar(@PathVariable Long id, @RequestBody Coordenadores novoCoordenador) {
    Coordenadores coordenadorExistente = coordenadoresService.buscarPorId(id);

    if (coordenadorExistente == null) {
        return ResponseEntity.notFound().build();
    } else {
        
        coordenadorExistente.setCargo(novoCoordenador.getCargo());;
        coordenadorExistente.setDatainiciocoordenacao(novoCoordenador.getDatainiciocoordenacao());
        coordenadorExistente.setDepartamento(novoCoordenador.getDepartamento());

       
        Coordenadores coordenadorAtualizado = coordenadoresService.alterar(coordenadorExistente);

        
        return ResponseEntity.ok(coordenadorAtualizado);
    }
}


 @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
         coordenadoresService.excluir(id);
         return ResponseEntity.ok().build();
    }

}
