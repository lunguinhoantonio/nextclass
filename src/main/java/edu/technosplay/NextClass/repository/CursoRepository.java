package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findAllByAtivo(boolean ativo);
    List<Curso> findAllByProfessorId(Long professorId);
}
