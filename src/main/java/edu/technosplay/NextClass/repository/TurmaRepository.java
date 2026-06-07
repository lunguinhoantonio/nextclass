package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    @Query("""
        SELECT t FROM Turma t
        WHERE (:cursoId IS NULL OR t.curso.id = :cursoId)
          AND (:ativa IS NULL OR t.ativa = :ativa)
    """)
    List<Turma> listar(@Param("cursoId") Long cursoId, @Param("ativa") Boolean ativa);

    @Query("""
        SELECT COUNT(m) FROM Matricula m
        WHERE m.turma.id = :turmaId
          AND m.status = 'ATIVA'
    """)
    int contarMatriculasAtivas(@Param("turmaId") Long turmaId);
}
