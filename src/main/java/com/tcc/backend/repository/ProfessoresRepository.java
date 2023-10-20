package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcc.backend.dto.ProfessoresRequest;
import com.tcc.backend.entity.Professores;

@Repository
public interface ProfessoresRepository extends JpaRepository<Professores, Long>{

    Professores saveAndFlush(ProfessoresRequest professoresRequest);  
}
