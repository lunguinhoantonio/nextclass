package edu.technosplay.NextClass.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MensagemAtendimentoRequest(
        @NotBlank(message = "Conteúdo é obrigatório")
        @Size(max = 2000, message = "Mensagem deve ter no máximo 2000 caracteres")
        @Schema(description = "Texto da mensagem", example = "Olá, gostaria de mais informações sobre meu atendimento.")
        String conteudo,

        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        @Schema(description = "Nome do remetente. Obrigatório apenas para solicitantes anônimos (sem autenticação).",
                example = "João da Silva")
        String nomeRemetente
) {
}
