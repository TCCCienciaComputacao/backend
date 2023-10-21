package com.tcc.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.TurmasProfessores;

@Repository
public interface TurmasProfessoresRepository  extends JpaRepository<TurmasProfessores, Long>{
    @Query("SELECT tp.professores FROM TurmasProfessores tp WHERE tp.turmas.id = :id")
    List<Professores> findByTurma(@Param("id") Long id);
}
