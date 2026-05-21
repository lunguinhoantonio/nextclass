package edu.technosplay.NextClass.dto.request;


import edu.technosplay.NextClass.model.enums.DiaSemana;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Schema(description = "Dados para criação ou atualização completa de um curso")
public record CursoRequest(
        @Schema(description = "Nome do curso", example = "Matemática Avançada")
        @NotBlank(message = "Nome do curso é obrigatório")
        String nome,

        @Schema(description = "Quantidade de vagas disponíveis", example = "30")
        @NotNull(message = "Vagas são obrigatórias")
        @Positive(message = "Número de vagas deve ser positivo")
        Integer qtdVagas,

        @Schema(description = "Horário de início das aulas no formato HH:mm", example = "08:00")
        @NotNull(message = "Horário de início é obrigatória")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato HH:mm")
        String horarioInicio,

        @Schema(description = "Horário de fim das aulas no formato HH:mm", example = "11:00")
        @NotNull(message = "Horário do fim é obrigatória")
        @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "Horário inválido. Use o formato HH:mm")
        String horarioFim,

        @Schema(description = "Dia da semana de início",
                example = "SEGUNDA",
                allowableValues = {"SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO"}
        )
        @NotNull(message = "Dia do início é obrigatória")
        DiaSemana diaInicio,

        @Schema(description = "Dia da semana de fim",
                example = "SEXTA",
                allowableValues = {"SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO", "DOMINGO"}
        )
        @NotNull(message = "Dia do fim é obrigatória")
        DiaSemana diaFim,

        @Schema(description = "ID do professor responsável pelo curso (opcional)", example = "1")
        Long professorId
) {
}
