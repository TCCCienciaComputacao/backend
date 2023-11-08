package com.tcc.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

import com.google.ortools.Loader;
import com.google.ortools.sat.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcc.backend.entity.Turmas;
import com.tcc.backend.dto.Grade;
import com.tcc.backend.entity.Disponibilidade;
import com.tcc.backend.entity.Professores;
import com.tcc.backend.repository.DisponibilidadeRepository;
import com.tcc.backend.repository.TurmasProfessoresRepository;
import com.tcc.backend.repository.TurmasRepository;

@Service
public class AulaService {

    @Autowired
    private TurmasProfessoresRepository turmasProfessoresRepository;

    @Autowired
    private TurmasRepository turmasRepository;
    @Autowired
    private DisponibilidadeRepository disponibilidadeRepository;

    public List<Grade> gradeAula(Long id) {
        Loader.loadNativeLibraries();

        List<List<String>> horariosProibidos = new ArrayList<>();
        List<List<String>> periodosProibidos = new ArrayList<>();
        List<Grade> gradesGeradas = new ArrayList<>();

        Optional<Turmas> turma = turmasRepository.findById(id);

        if (turma.isPresent()) {
            List<Professores> professoresDaTurma = turmasProfessoresRepository.findByTurma(id);

            final int numProfessores = professoresDaTurma.size();
            final int numPeriodos = 5;
            final int numHoras = 2;

            List<Integer> diasDaSemana = new ArrayList<>();
            for (int i = 1; i <= numPeriodos; i++) {
                diasDaSemana.add(i);
            }

            for (Professores professores : professoresDaTurma) {
                List<Disponibilidade> disponibilidadeProfessor = disponibilidadeRepository
                        .findByProfessores(professores);
                List<Integer> diasIndisponiveis = new ArrayList<>();

                for (Disponibilidade disponibilidade : disponibilidadeProfessor) {
                    diasIndisponiveis.add(disponibilidade.getDiasemana());
                }

                List<String> diasProibidos = new ArrayList<>();
                for (Integer dia : diasDaSemana) {
                    if (!diasIndisponiveis.contains(dia)) {
                        diasProibidos.add(String.valueOf(dia));
                    }
                }
                periodosProibidos.add(diasProibidos);
            }

            for (Professores professor : professoresDaTurma) {
                List<Disponibilidade> disponibilidadeProfessor = disponibilidadeRepository.findByProfessores(professor);
                List<String> horariosProibidosProfessor = new ArrayList<>();

                for (Integer dia : diasDaSemana) {
                    boolean diaDisponivel = true;

                    for (Disponibilidade disponibilidade : disponibilidadeProfessor) {
                        if (disponibilidade.getDiasemana() == dia) {
                            for (int hora = 1; hora <= numHoras; hora++) {
                                if (hora >= disponibilidade.getHorarioinicio()
                                        && hora <= disponibilidade.getHorariofim()) {
                                    diaDisponivel = false;
                                    break;
                                }
                            }
                        }
                    }

                    if (!diaDisponivel) {
                        horariosProibidosProfessor.add(String.valueOf(dia));
                    }
                }

                horariosProibidos.add(horariosProibidosProfessor);
            }

            final int[] allProfessores = IntStream.range(0, numProfessores).toArray();
            final int[] allPeriodos = IntStream.range(0, numPeriodos).toArray();
            final int[] allHoras = IntStream.range(0, numHoras).toArray();

            CpModel model = new CpModel();

            BoolVar[][][] shifts = new BoolVar[numPeriodos][numHoras][numProfessores];

            for (int n : allPeriodos) {
                for (int d : allHoras) {
                    for (int s : allProfessores) {
                        shifts[n][d][s] = model.newBoolVar("shifts_n" + n + "d" + d + "s" + s);
                    }
                }
            }

            for (int n : allPeriodos) {
                for (int d : allHoras) {
                    List<BoolVar> professors = new ArrayList<>();
                    for (int s : allProfessores) {
                        professors.add(shifts[n][d][s]);
                    }
                    model.addEquality(LinearExpr.sum(professors.toArray(new BoolVar[0])), 1);
                }
            }

            for (int s : allProfessores) {
                for (int n : allPeriodos) {
                    List<BoolVar> work = new ArrayList<>();
                    for (int d : allHoras) {
                        work.add(shifts[n][d][s]);
                    }
                    model.addLessOrEqual(LinearExpr.sum(work.toArray(new BoolVar[0])), 1);
                }
            }

            for (int i = 0; i < shifts.length; i++) {
                for (int j = 0; j < shifts[i].length; j++) {
                    for (int k = 0; k < shifts[i][j].length; k++) {
                        // Verifique se o professor k está associado ao índice i
                        if (k < professoresDaTurma.size() && periodosProibidos.get(k).contains(String.valueOf(i + 1))) {
                            model.addEquality(shifts[i][j][k], 0);
                        }
                    }
                }
            }

            // Create a CP solver
            CpSolver solver = new CpSolver();

            // Define the number of solutions to find
            final int solutionLimit = 1;

            // Create the solution callback object
            final class VarArraySolutionPrinterWithLimit extends CpSolverSolutionCallback {
                public VarArraySolutionPrinterWithLimit(
                        int[] allProfessores, int[] allPeriodos, int[] allHoras, BoolVar[][][] shifts, int limit) {
                    solutionCount = 0;
                    this.allProfessores = allProfessores;
                    this.allHoras = allHoras;
                    this.allPeriodos = allPeriodos;
                    this.shifts = shifts;
                    printedSolutions = new HashSet<>();
                }

                @Override
                public void onSolutionCallback() {
                    if (solutionCount == 0) {
                        SolutionData data = collectSolutionData();
                        if (!printedSolutions.contains(data)) {
                            System.out.printf("Solution #%d:%n", solutionCount);
                            int linesPrinted = 0;
                            for (int s : allProfessores) {
                                for (int n : allPeriodos) {
                                    for (int d : allHoras) {
                                        if (booleanValue(shifts[n][d][s])) {
                                            String professorName = professoresDaTurma.get(s).getUsuario().getNome();
                                            Grade grade = new Grade(professorName, n + 1, d + 1);
                                            gradesGeradas.add(grade);

                                            // Imprima a atribuição da aula
                                            System.out.printf("  Professor %s took period %d at hour %d\n",
                                                    grade.getProfessorName(), grade.getPeriodo(), grade.getHora());
                                            linesPrinted++;

                                            if (linesPrinted >= 10) {
                                                break; // Limite a 10 linhas por solução
                                            }
                                        }
                                    }
                                    if (linesPrinted >= 10) {
                                        break; // Limite a 10 linhas por solução
                                    }
                                }
                                if (linesPrinted >= 10) {
                                    break; // Limite a 10 linhas por solução
                                }
                            }

                            // Agora, você tem uma lista de aulas geradas (gradesGeradas)
                            // Adicione a solução à lista de soluções impressas
                            printedSolutions.add(data);

                            solutionCount++;
                            System.out.printf("Stop search after %d solutions%n", 1); // Pare a busca após encontrar 1 solução
                            stopSearch();
                        }
                    }
                }

                public int getSolutionCount() {
                    return solutionCount;
                }

                private int solutionCount;
                private final int[] allProfessores;
                private final int[] allPeriodos;
                private final int[] allHoras;
                private final BoolVar[][][] shifts;
                private Set<SolutionData> printedSolutions;

                // Classe auxiliar para coletar dados da solução
                final class SolutionData {
                    int[][][] values;

                    public SolutionData(int[][][] values) {
                        this.values = values;
                    }

                    @Override
                    public int hashCode() {
                        return java.util.Arrays.deepHashCode(values);
                    }

                    @Override
                    public boolean equals(Object obj) {
                        if (this == obj) {
                            return true;
                        }
                        if (obj == null || getClass() != obj.getClass()) {
                            return false;
                        }
                        SolutionData other = (SolutionData) obj;
                        return java.util.Arrays.deepEquals(values, other.values);
                    }
                }

                private SolutionData collectSolutionData() {
                    int[][][] values = new int[allPeriodos.length][allHoras.length][allProfessores.length];
                    for (int s : allProfessores) {
                        for (int n : allPeriodos) {
                            for (int d : allHoras) {
                                values[n][d][s] = booleanValue(shifts[n][d][s]) ? 1 : 0;
                            }
                        }
                    }
                    return new SolutionData(values);
                }
            }

            // Create the solution callback object
            VarArraySolutionPrinterWithLimit cb = new VarArraySolutionPrinterWithLimit(allProfessores, allPeriodos,
                    allHoras, shifts, solutionLimit);

            // Solve the model and print the results
            CpSolverStatus status = solver.solve(model, cb);
            System.out.println("Status: " + status);
            System.out.println(cb.getSolutionCount() + " solutions found.");
        }
        return gradesGeradas;
    }
}
