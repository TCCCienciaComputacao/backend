package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Materias;

@Repository
public interface MateriasRepository extends JpaRepository<Materias, Long> {
    
}
