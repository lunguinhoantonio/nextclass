package edu.technosplay.NextClass.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UsuarioResponse(
        @Schema(description = "ID do usuário", example = "1")
        Long id,

        @Schema(description = "Nome completo", example = "João da Silva")
        String nome,

        @Schema(description = "CPF do usuário", example = "12345678909")
        String cpf,

        @Schema(description = "E-mail do usuário", example = "joao@email.com")
        String email,

        @Schema(description = "Telefone de contato", example = "71999999999")
        String telefone,

        @Schema(description = "Data de nascimento", example = "15/03/2000")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        @Schema(description = "Papel do usuário no sistema", example = "PROFESSOR")
        Role role,

        @Schema(description = "Indica se o usuário está ativo", example = "true")
        boolean ativo,

        @Schema(description = "Logradouro do endereço", example = "Rua das Flores")
        String logradouro,

        @Schema(description = "Número do endereço", example = "123")
        String numero,

        @Schema(description = "Complemento do endereço", example = "Apto 45")
        String complemento,

        @Schema(description = "Bairro", example = "Brotas")
        String bairro,

        @Schema(description = "Cidade", example = "Salvador")
        String cidade,

        @Schema(description = "Estado (UF)", examples = {"BA", "Bahia"})
        String estado,

        @Schema(description = "CEP", example = "40000000")
        String cep,

        @Schema(description = "Data e hora de criação", example = "01/05/2026 10:00:00")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,

        @Schema(description = "Data e hora da última atualização", example = "10/05/2026 15:30:00")
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {
}
