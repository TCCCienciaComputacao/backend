package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Aula;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {
    
}
