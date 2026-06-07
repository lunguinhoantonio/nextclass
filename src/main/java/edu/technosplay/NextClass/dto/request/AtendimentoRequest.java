package edu.technosplay.NextClass.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "Dados para abertura de um atendimento")
public record AtendimentoRequest(
        @NotNull(message = "Tipo de atendimento é obrigatório")
        @Schema(description = "Categoria do atendimento", example = "FINANCEIRO")
        TipoAtendimento tipo,

        @NotNull(message = "Data de agendamento é obrigatória")
        @Future(message = "Data de agendamento deve ser futura")
        @Schema(description = "Data e hora desejada", example = "2026-06-20T14:00:00")
        LocalDateTime dataAgendamento,

        @NotBlank(message = "Nome completo é obrigatório")
        @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O campo deve conter apenas letras.")
        @Schema(description = "Nome completo do solicitante", example = "João da Silva")
        String nomeCompleto,

        @Schema(description = "CPF sem formatação, apenas 11 dígitos numéricos", example = "12345678909")
        @NotBlank(message = "CPF é obrigatório")
        //@CPF(message = "CPF inválido")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
        String cpf,

        @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "15/03/2000")
        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento inválida")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @Schema(description = "Endereço de e-mail", example = "joao@email.com")
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
        String email,

        @Schema(description = "Telefone de contato sem formatação, apenas os 11 dígitos numéricos", example = "71987654321")
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\d{11}", message = "Telefone inválido. Use somente valores numéricos")
        String telefone,

        @NotBlank(message = "Assunto é obrigatório")
        @Size(max = 200, message = "Assunto deve ter no máximo 200 caracteres")
        @Schema(description = "Resumo do motivo", example = "Boleto de maio não foi gerado")
        String assunto,

        @NotBlank(message = "Descrição é obrigatória")
        @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
        @Schema(description = "Descrição detalhada do problema")
        String descricao,

        @Schema(description = "ID do atendente preferencial (opcional). Deve ser um usuário com a role ATENDENTE.", example = "3")
        Long atendenteId
) {
}
