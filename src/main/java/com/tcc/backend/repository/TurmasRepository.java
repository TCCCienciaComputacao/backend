package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Turmas;

@Repository
public interface TurmasRepository extends JpaRepository<Turmas, Long> {
    Turmas findByNometurma(String nometurma);
}
