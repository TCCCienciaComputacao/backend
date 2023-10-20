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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcc.backend.dto.ProfessoresRequest;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.service.ProfessoresService;
import com.tcc.backend.service.UsuariosService;

@RequestMapping("/api/professores")
@RestController
@CrossOrigin(origins = "*") 
public class ProfessoresController {

    @Autowired
    private ProfessoresService professoresService;

    @Autowired
    private UsuariosService usuariosService;

      @GetMapping("/")
    public List<Professores> buscarTodos(){
        return professoresService.buscarTodos();
    }

    @PostMapping("/")
    public ResponseEntity<Object> inserir(@RequestBody ProfessoresRequest request, @RequestParam Long usuarioId) {
        Usuarios usuario = usuariosService.buscarPorId(usuarioId);
        if (usuario == null) {
            String mensagemDeErro = "Usuário não encontrado";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemDeErro);
        }
    
        Professores novoProfessor = new Professores();
        novoProfessor.setFormacaoacademica(request.getFormacaoacademica());
        novoProfessor.setDatacontratacao(request.getDatacontratacao());
    
        // Defina o usuário no novo professor
        novoProfessor.setUsuario(usuario);
    
        // Agora, insira as datas de disponibilidade do request no professor
    
        Professores professorCriado = professoresService.inserir(novoProfessor);
    
        return ResponseEntity.ok(professorCriado);
    }


@PutMapping("/{id}")
public ResponseEntity<Professores> alterar(@PathVariable Long id, @RequestBody Professores novoProfessor) {
    // Verifica se o professor com o ID fornecido existe no banco de dados.
    Professores professorExistente = professoresService.buscarPorId(id);

    if (professorExistente == null) {
        // Se o professor não existir, retorne um status 404 (Not Found).
        return ResponseEntity.notFound().build();
    } else {
        // Caso contrário, atualize os campos do professor existente com os novos dados.
        professorExistente.setDatacontratacao(novoProfessor.getDatacontratacao());
        professorExistente.setFormacaoacademica(novoProfessor.getFormacaoacademica());

        // Chame o serviço para salvar as alterações.
        Professores professorAtualizado = professoresService.alterar(professorExistente);

        // Retorne o professor atualizado e um status 200 (OK).
        return ResponseEntity.ok(professorAtualizado);
    }
}

 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id){
         professoresService.excluir(id);
         return ResponseEntity.ok().build();
    }



    
}
