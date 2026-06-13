package edu.technosplay.NextClass.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record CursoResponse(
        @Schema(description = "ID do curso", example = "1")
        Long id,

        @Schema(description = "Nome do curso", example = "Matemática Avançada")
        String nome,

        @Schema(description = "Quantidade de vagas disponíveis", example = "30")
        Integer qtdVagas,

        @Schema(description = "Vagas disponíveis (vagas totais menos matrículas ativas)", example = "18")
        int vagasDisponiveis,

        @Schema(description = "Horário de início das aulas", example = "08:00:00")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioInicio,

        @Schema(description = "Horário de início das aulas", example = "10:00:00")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horarioFim,

        @Schema(description = "Dia da semana de início", example = "SEGUNDA")
        DiaSemana diaInicio,

        @Schema(description = "Dia da semana de fim", example = "SEXTA")
        DiaSemana diaFim,

        @Schema(description = "Indica se o curso está ativo", example = "true")
        boolean ativo,

        @Schema(description = "Professor responsável pelo curso")
        UsuarioResponse professor,

        @Schema(description = "Data e hora de criação", example = "2026-05-01T10:00:00")
        LocalDateTime criadoEm,

        @Schema(description = "Data e hora da última atualização", example = "2026-05-10T15:30:00")
        LocalDateTime atualizadoEm
) {
}
