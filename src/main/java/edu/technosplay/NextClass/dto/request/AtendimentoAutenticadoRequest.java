package edu.technosplay.NextClass.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Schema(description = "Dados para abertura de atendimento por usuário autenticado. " +
        "Dados pessoais são obtidos automaticamente do usuário logado.")
public record AtendimentoAutenticadoRequest(

        @NotNull(message = "Tipo de atendimento é obrigatório")
        @Schema(description = "Categoria do atendimento", example = "ACADEMICO")
        TipoAtendimento tipo,

        @NotNull(message = "Data de agendamento é obrigatória")
        @Future(message = "Data de agendamento deve ser futura")
        @Schema(description = "Data e hora desejada", example = "2026-06-20T14:00:00")
        LocalDateTime dataAgendamento,

        @NotBlank(message = "Assunto é obrigatório")
        @Size(max = 200, message = "Assunto deve ter no máximo 200 caracteres")
        @Schema(description = "Resumo do motivo", example = "Dúvida sobre nota do semestre")
        String assunto,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
        @Schema(description = "Descrição detalhada do problema")
        String descricao,

        @Schema(description = "ID do atendente preferencial (opcional). Deve ter a role ATENDENTE.", example = "3")
        Long atendenteId
) {}