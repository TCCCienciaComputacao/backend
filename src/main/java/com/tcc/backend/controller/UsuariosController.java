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

import com.tcc.backend.dto.UsuariosLogin;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.service.UsuariosService;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*") 
public class UsuariosController {
    
    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("/")
    public List<Usuarios> buscarTodos(){
        return usuariosService.buscarTodos();
    }

    @PostMapping("/")
    public ResponseEntity<Object> inserir(@RequestBody Usuarios usuarios) {
        Usuarios usuarioCadastrado = usuariosService.findByEmail(usuarios.getEmail());
        
        if (usuarioCadastrado != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        }  
        usuariosService.inserir(usuarios); 
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro efetuado com sucesso.");
    }
    

    @PutMapping("/")
    public Usuarios alterar(@RequestBody Usuarios usuarios){
        return usuariosService.alterar(usuarios);
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
         usuariosService.excluir(id);
         return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> autenticar(@RequestBody UsuariosLogin loginRequest) {
        String email = loginRequest.getEmail();
        String senha = loginRequest.getSenha();
    
        Usuarios usuarioAutenticado = usuariosService.verificarCredenciais(email, senha);
    
        if (usuarioAutenticado != null) {
            return ResponseEntity.status(HttpStatus.OK).body("Usuário autenticado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }
}
