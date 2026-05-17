package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCpf(String cpf);
    Optional<Usuario> findByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    List<Usuario> findAllByRole(Role role);
    List<Usuario> findAllByAtivo(boolean ativo);
    //List<Usuario> findByRole(Role role, boolean ativo);
}
