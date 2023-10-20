package com.tcc.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tcc.backend.entity.Periodos;


@Repository
public interface PeriodosRepository extends JpaRepository<Periodos, Long>{
    
}
