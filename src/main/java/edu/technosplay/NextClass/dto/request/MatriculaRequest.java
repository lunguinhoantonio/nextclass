package edu.technosplay.NextClass.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para matrícula de um aluno em uma turma")
public record MatriculaRequest(
        @Schema(description = "ID da turma na qual o aluno deseja se matricular", example = "3")
        @NotNull(message = "ID da turma é obrigatório")
        Long turmaId
) {
}
