package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Aluno;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Optional<Aluno> findByCpf(String cpf);
    Optional<Aluno> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Page<Aluno> findByStatus(StatusAluno status, Pageable pageable);

    /*@Query("SELECT a FROM Aluno a WHERE " +
            "LOWER(a.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
            "a.cpf LIKE CONCAT('%', :termo, '%') OR " +
            "LOWER(a.email) LIKE LOWER(CONCAT('%', :termo, '%'))")
    Page<Aluno> buscarPorTermo(@Param("termo") String termo, Pageable pageable);*/
}
