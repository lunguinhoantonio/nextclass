package edu.technosplay.NextClass.repository;

import edu.technosplay.NextClass.model.Atendimento;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {
    List<Atendimento> findAllBySolicitanteIdOrderByDataAgendamentoDesc(Long solicitanteId);
    List<Atendimento> findAllByAtendenteIdOrderByDataAgendamentoAsc(Long atendenteId);
    List<Atendimento> findAllByAtendenteIsNull();

    @Query("SELECT a FROM Atendimento a WHERE " +
            "(:tipo IS NULL OR a.tipo = :tipo) AND " +
            "(:status IS NULL OR a.status = :status)")
    List<Atendimento> findAllComFiltros(
            @Param("tipo") TipoAtendimento tipo,
            @Param("status") StatusAtendimento status,
            Sort sort);
}
