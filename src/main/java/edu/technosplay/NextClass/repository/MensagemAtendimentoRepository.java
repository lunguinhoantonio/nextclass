package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.MensagemAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemAtendimentoRepository extends JpaRepository<MensagemAtendimento, Long> {
    List<MensagemAtendimento> findAllByAtendimentoIdOrderByEnviadoEmAsc(Long atendimentoId);
}
