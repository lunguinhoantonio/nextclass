package edu.technosplay.NextClass.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Dados para criação de uma turma")
public record TurmaRequest(
        @Schema(description = "Código identificador da turma", example = "MAT-2026-A")
        @NotBlank(message = "Código da turma é obrigatório")
        String codigo,

        @Schema(description = "ID do curso ao qual a turma pertence", example = "1")
        @NotNull(message = "ID do curso é obrigatório")
        Long cursoId
) {
}
