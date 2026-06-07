package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.TurmaResponse;
import edu.technosplay.NextClass.model.Turma;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TurmaMapper {
    public static TurmaResponse toResponse(Turma turma, int vagasOcupadas) {
        int vagasTotais = turma.getCurso().getQtdVagas();
        return TurmaResponse.builder()
                .id(turma.getId())
                .codigo(turma.getCodigo())
                .curso(CursoMapper.toResponse(turma.getCurso()))
                .vagasTotais(vagasTotais)
                .vagasOcupadas(vagasOcupadas)
                .vagasDisponiveis(vagasTotais - vagasOcupadas)
                .ativa(turma.isAtiva())
                .criadoEm(turma.getCriadoEm())
                .build();
    }
}
