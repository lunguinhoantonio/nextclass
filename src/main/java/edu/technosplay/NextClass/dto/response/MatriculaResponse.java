package edu.technosplay.NextClass.dto.response;

import edu.technosplay.NextClass.model.enums.StatusMatricula;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MatriculaResponse(
        @Schema(description = "ID da matrícula", example = "1")
        Long id,

        @Schema(description = "Aluno matriculado")
        UsuarioResponse aluno,

        @Schema(description = "Turma da matrícula")
        TurmaResponse turma,

        @Schema(description = "Status da matrícula", example = "ATIVA")
        StatusMatricula status,

        @Schema(description = "Data da matrícula")
        LocalDateTime criadoEm
) {
}
