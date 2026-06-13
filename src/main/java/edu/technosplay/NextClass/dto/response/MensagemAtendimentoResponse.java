package edu.technosplay.NextClass.dto.response;

import edu.technosplay.NextClass.model.enums.TipoRemetente;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MensagemAtendimentoResponse(
        Long id,

        @Schema(description = "Nome exibido do remetente")
        String nomeRemetente,

        @Schema(description = "Texto da mensagem")
        String conteudo,

        @Schema(description = "Lado que enviou a mensagem: SOLICITANTE ou ATENDENTE")
        TipoRemetente tipoRemetente,

        @Schema(description = "Data e hora do envio")
        LocalDateTime enviadoEm
) {
}
