package edu.technosplay.NextClass.dto.request;

import edu.technosplay.NextClass.model.enums.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para atualização parcial de um curso. Todos os campos são opcionais")
public record CursoPatchRequest(
        @Schema(description = "Nome do curso", example = "Matemática Avançada")
        String nome,

        @Schema(description = "Quantidade de vagas disponíveis", example = "30")
        @Positive(message = "Número de vagas deve ser positivo")
        Integer qtdVagas,

        @Schema(description = "Horário de início das aulas no formato HH:mm", example = "08:00")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato HH:mm")
        String horarioInicio,

        @Schema(description = "Horário de início das aulas no formato HH:mm", example = "11:00")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato HH:mm")
        String horarioFim,

        @Schema(description = "Dia da semana de início", example = "SEGUNDA")
        DiaSemana diaInicio,
        @Schema(description = "Dia da semana de fim", example = "SEXTA")
        DiaSemana diaFim,

        @Schema(description = "ID do professor responsável pelo curso", example = "1")
        Long professorId
) {
}
