package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.model.Curso;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CursoMapper {
    public static CursoResponse toResponse(Curso curso) {
        return CursoResponse.builder()
                .id(curso.getId())
                .nome(curso.getNome())
                .qtdVagas(curso.getQtdVagas())
                .horarioInicio(curso.getHorarioInicio())
                .horarioFim(curso.getHorarioFim())
                .diaInicio(curso.getDiaInicio())
                .diaFim(curso.getDiaFim())
                .ativo(curso.isAtivo())
                .criadoEm(curso.getCriadoEm())
                .atualizadoEm(curso.getAtualizadoEm())
                .build();
    }
}
