package com.tcc.backend.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.entity.Professores;

@Repository
public interface DisponibilidadeRepository  extends JpaRepository<Disponibilidade, Long>{

    List<Disponibilidade> findByProfessores(@Param("professores") Professores professores);

    List<Disponibilidade> findByProfessoresAndDiasemana(Professores professores, DayOfWeek diasemana);
    
}
