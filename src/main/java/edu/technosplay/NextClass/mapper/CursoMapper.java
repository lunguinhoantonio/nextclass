package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.model.Curso;
import lombok.experimental.UtilityClass;

import java.util.Optional;

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
                .professor(Optional.ofNullable(curso.getProfessor()).map(UsuarioMapper::toResponse).orElse(null))
                .criadoEm(curso.getCriadoEm())
                .atualizadoEm(curso.getAtualizadoEm())
                .build();
    }
}
