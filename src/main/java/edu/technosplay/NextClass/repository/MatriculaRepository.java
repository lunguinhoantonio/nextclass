package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Matricula;
import edu.technosplay.NextClass.model.enums.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findAllByAlunoId(Long alunoId);

    Optional<Matricula> findByAlunoIdAndTurmaId(Long alunoId, Long turmaId);

    boolean existsByAlunoIdAndTurmaIdAndStatus(Long alunoId, Long turmaId, StatusMatricula status);


    @Query("""
        SELECT COUNT(m) FROM Matricula m
        WHERE m.aluno.id = :alunoId
          AND m.status = 'ATIVA'
    """)
    int contarTurmasAtivasDoAluno(@Param("alunoId") Long alunoId);

    @Query("""
        SELECT COUNT(m) > 0 FROM Matricula m
        WHERE m.aluno.id = :alunoId
          AND m.turma.id = :turmaId
          AND m.status = 'ATIVA'
    """)
    boolean alunoJaMatriculadoNaTurma(@Param("alunoId") Long alunoId, @Param("turmaId") Long turmaId);
}
