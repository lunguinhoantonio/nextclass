package edu.technosplay.NextClass.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record TurmaResponse(
        @Schema(description = "ID da turma", example = "1")
        Long id,

        @Schema(description = "Código da turma", example = "MAT-2026-A")
        String codigo,

        @Schema(description = "Curso da turma")
        CursoResponse curso,

        @Schema(description = "Vagas totais do curso", example = "30")
        Integer vagasTotais,

        @Schema(description = "Vagas ocupadas", example = "12")
        int vagasOcupadas,

        @Schema(description = "Vagas disponíveis", example = "18")
        int vagasDisponiveis,

        @Schema(description = "Indica se a turma está ativa", example = "true")
        boolean ativa,

        @Schema(description = "Data de criação")
        LocalDateTime criadoEm
) {
}
