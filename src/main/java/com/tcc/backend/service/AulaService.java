package com.tcc.backend.service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Aula;
import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.entity.Turmas;
import com.tcc.backend.repository.DisponibilidadeRepository;
import com.tcc.backend.repository.ProfessoresRepository;
import com.tcc.backend.repository.TurmasRepository;

@Service
public class AulaService {

    @Autowired
    private ProfessoresRepository professoresRepository;
    @Autowired
    private TurmasRepository turmasRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public List<Aula> organizarAulasParaTurmaNaSemana(String nometurma) {
        List<Aula> aulasOrganizadas = new ArrayList<>();
        List<Aula> aulasComConflito = new ArrayList<>();

        Turmas turma = turmasRepository.findByNometurma(nometurma);

        Map<DayOfWeek, List<Disponibilidade>> disponibilidadesPorDia = new EnumMap<>(DayOfWeek.class);
        for (DayOfWeek diaSemana : DayOfWeek.values()) {
            disponibilidadesPorDia.put(diaSemana, new ArrayList<>());
        }

        List<Professores> professores = professoresRepository.findAll();
        for (Professores professor : professores) {
            List<Disponibilidade> disponibilidades = disponibilidadeRepository.findByProfessores(professor);
            for (Disponibilidade disponibilidade : disponibilidades) {
                DayOfWeek diaSemana = disponibilidade.getDiasemana();
                disponibilidadesPorDia.get(diaSemana).add(disponibilidade);
            }
        }

        for (DayOfWeek diaSemana : DayOfWeek.values()) {
            List<Disponibilidade> disponibilidadesDoDia = disponibilidadesPorDia.get(diaSemana);
            if (!disponibilidadesDoDia.isEmpty()) {
                for (int i = 0; i < 2 && i < disponibilidadesDoDia.size(); i++) {
                    Disponibilidade disponibilidade = disponibilidadesDoDia.get(i);
                    Aula aula = criarAula(turma, disponibilidade);
                    aulasOrganizadas.add(aula);
                }
                if (disponibilidadesDoDia.size() > 1) {
                    for (int i = 0; i < disponibilidadesDoDia.size() - 1; i++) {
                        for (int j = i + 1; j < disponibilidadesDoDia.size(); j++) {
                            Disponibilidade disponibilidade1 = disponibilidadesDoDia.get(i);
                            Disponibilidade disponibilidade2 = disponibilidadesDoDia.get(j);
                            if (horariosConflitantes(disponibilidade1, disponibilidade2)) {
                                aulasComConflito.add(criarAula(turma, disponibilidade1));
                                aulasComConflito.add(criarAula(turma, disponibilidade2));
                            }
                        }
                    }
                }
            } else {
                for (Disponibilidade disponibilidade : disponibilidadesDoDia) {
                    aulasComConflito.add(criarAula(turma, disponibilidade));
                }
            }
        }

        return aulasOrganizadas;
    }

    public List<Aula> getAulasComConflito(List<Aula> aulasOrganizadas) {
        List<Aula> aulasComConflito = new ArrayList<>();
        
        for (int i = 0; i < aulasOrganizadas.size(); i++) {
            Aula aula1 = aulasOrganizadas.get(i);
            for (int j = i + 1; j < aulasOrganizadas.size(); j++) {
                Aula aula2 = aulasOrganizadas.get(j);
                if (horariosConflitantes(aula1, aula2)) {
                    // Há um conflito de horário entre aula1 e aula2
                    aulasComConflito.add(aula1);
                    aulasComConflito.add(aula2);
                }
            }
        }
    
        return aulasComConflito;
    }

    private Aula criarAula(Turmas turma, Disponibilidade disponibilidade) {
        Aula aula = new Aula();
        aula.setProfessores(disponibilidade.getProfessores());
        aula.setTurmas(turma);
        aula.setDiasemana(disponibilidade.getDiasemana());
        aula.setHorarioinicio(disponibilidade.getHorarioinicio());
        aula.setHorariofim(disponibilidade.getHorariofim());
        return aula;
    }

    private boolean horariosConflitantes(Aula aula1, Aula aula2) {
        return aula1.getDiasemana() == aula2.getDiasemana() &&
               aula1.getHorarioinicio().isBefore(aula2.getHorariofim()) &&
               aula1.getHorariofim().isAfter(aula2.getHorarioinicio());
    }
    
    private boolean horariosConflitantes(Disponibilidade disponibilidade1, Disponibilidade disponibilidade2) {
        return disponibilidade1.getHorarioinicio().isBefore(disponibilidade2.getHorariofim()) &&
               disponibilidade1.getHorariofim().isAfter(disponibilidade2.getHorarioinicio());
    }
}
