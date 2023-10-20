package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Coordenadores;

@Repository
public interface CoordenadoresRepository extends JpaRepository<Coordenadores, Long> {
    
}
