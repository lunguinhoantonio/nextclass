package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Atendimento;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findAllBySolicitanteIdOrderByDataAgendamentoDesc(Long solicitanteId);
    List<Atendimento> findAllByAtendenteIdOrderByDataAgendamentoAsc(Long atendenteId);
    List<Atendimento> findAllByStatus(StatusAtendimento status);
    List<Atendimento> findAllByTipo(TipoAtendimento tipo);
    List<Atendimento> findAllByTipoAndStatus(TipoAtendimento tipo, StatusAtendimento status);
    List<Atendimento> findAllByAtendenteIsNull();
}
