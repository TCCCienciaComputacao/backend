package com.tcc.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcc.backend.dto.MateriasProfessoresProjection;

import com.tcc.backend.entity.MateriasProfessores;
import com.tcc.backend.entity.Professores;

@Repository
public interface MateriasProfessoresRepository  extends JpaRepository<MateriasProfessores, Long>{
    @Query("SELECT tp.professores FROM MateriasProfessores tp WHERE tp.materias.id = :id")
    List<Professores> findByMateria(@Param("id") Long id);

    @Query("SELECT tp.id AS id, tp.materias.nomemateria AS nomeMateria, tp.professores.usuario.nome AS nomeProfessor FROM MateriasProfessores tp ORDER BY tp.id ASC")
    List<MateriasProfessoresProjection> getMateriasAndProfessores();

    @Query("SELECT tp FROM MateriasProfessores tp WHERE tp.materias.id = :materiaId AND tp.professores.id = :professorId")
    MateriasProfessores findByMateriaIdAndProfessorId(Long materiaId, Long professorId);

    @Query("SELECT tp FROM MateriasProfessores tp WHERE tp.materias.id = :materiaID")
    List<MateriasProfessores> findByMateriaId(@Param("materiaID") Long materiaId);
}
