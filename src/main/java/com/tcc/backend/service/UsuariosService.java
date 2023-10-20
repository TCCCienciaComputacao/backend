package com.tcc.backend.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Enderecos;
import com.tcc.backend.entity.Usuarios;
import com.tcc.backend.repository.EnderecosRepository;
import com.tcc.backend.repository.UsuariosRepository;

@Service
public class UsuariosService {
    
    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private EnderecosRepository enderecosRepository;

    public List<Usuarios> buscarTodos(){
        return usuariosRepository.findAll();
    }
    public Usuarios buscarPorId(Long id){
        return usuariosRepository.findById(id).orElse(null);
    }

    public Usuarios inserir(Usuarios usuarios){

        if (usuarios.getEnderecos() != null && usuarios.getEnderecos().getId() != null) {
            Enderecos endereco = enderecosRepository.findById(usuarios.getEnderecos().getId()).orElse(null);
            if (endereco != null) {
                usuarios.setEnderecos(endereco);
            }
        }

        Usuarios usuarioNovo = usuariosRepository.saveAndFlush(usuarios);
        return usuarioNovo;
        
    }

    public Usuarios alterar(Usuarios usuarios){

          if (usuarios.getEnderecos() != null && usuarios.getEnderecos().getId() != null) {
            Enderecos endereco = enderecosRepository.findById(usuarios.getEnderecos().getId()).orElse(null);
            if (endereco != null) {
                usuarios.setEnderecos(endereco);
            }
        }
        return usuariosRepository.saveAndFlush(usuarios);
    }

    public void excluir(Long id){
        Usuarios usuarios = usuariosRepository.findById(id).get();
        usuariosRepository.delete(usuarios);
    }


    public Usuarios verificarCredenciais(String email, String senha) {
        Usuarios usuario = usuariosRepository.findByEmail(email);
        if (usuario == null) {
            // Usuário não encontrado com o email fornecido
            return null;
        }
    
        if (verificarSenha(senha, usuario.getSenha())) {
            // Senha está correta
            return usuario;
        }
    
        // Senha incorreta
        return null;
    }

    public boolean verificarSenha(String senha, String senhaNoBanco) {
        // Compare os hashes das senhas
        return senhaNoBanco.equals(senha);
    }

    public Usuarios findByEmail(String email) {
        // Utilize o método do repositório para buscar um usuário por email
        return usuariosRepository.findByEmail(email);
    }


}
