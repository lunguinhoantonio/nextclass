package edu.technosplay.NextClass.dto.response;

import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Schema(description = "Dados de um atendimento")
@Builder
public record AtendimentoResponse(
        Long id,

        @Schema(description = "Nome completo do solicitante")
        String nomeCompleto,

        @Schema(description = "Telefone do solicitante")
        String telefone,

        @Schema(description = "E-mail do solicitante")
        String email,

        @Schema(description = "Quem abriu o chamado")
        UsuarioResponse solicitante,

        @Schema(description = "Quem vai realizar o atendimento (pode ser null se ainda não atribuído)")
        UsuarioResponse atendente,

        @Schema(
                description = "Tipo do atendimento",
                example = "FINANCEIRO",
                allowableValues = {"FINANCEIRO", "SUPORTE", "ACADEMICO", "OUTRO"}
        )
        TipoAtendimento tipo,

        @Schema(description = "Label legível do tipo", example = "Financeiro")
        String tipoLabel,

        @Schema(description = "Resumo do motivo", example = "Boleto de maio não foi gerado")
        String assunto,

        @Schema(description = "Descrição detalhada do problema")
        String descricao,

        @Schema(description = "Data e hora desejada", example = "2026-06-20T14:00:00")
        LocalDateTime dataAgendamento,

        @Schema(description = "Data e hora da realização do atendimento", example = "2026-06-20T16:00:00")
        LocalDateTime dataRealizacao,

        @Schema(
                description = "Andamento do atendimento",
                example = "AGENDADO",
                allowableValues = {"AGENDADO", "CONFIRMADO", "REALIZADO", "CANCELADO"}
        )
        StatusAtendimento status,

        @Schema(description = "Label legível do status", example = "Confirmado")
        String statusLabel,

        @Schema(description = "Data e hora em que o atendimento foi criado")
        LocalDateTime criadoEm

) {
}
