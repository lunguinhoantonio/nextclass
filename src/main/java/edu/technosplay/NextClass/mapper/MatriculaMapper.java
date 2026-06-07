package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.MatriculaResponse;
import edu.technosplay.NextClass.model.Matricula;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MatriculaMapper {
    public static MatriculaResponse toResponse(Matricula matricula, int vagasOcupadas) {
        return MatriculaResponse.builder()
                .id(matricula.getId())
                .aluno(UsuarioMapper.toResponse(matricula.getAluno()))
                .turma(TurmaMapper.toResponse(matricula.getTurma(), vagasOcupadas))
                .status(matricula.getStatus())
                .criadoEm(matricula.getCriadoEm())
                .build();
    }
}
