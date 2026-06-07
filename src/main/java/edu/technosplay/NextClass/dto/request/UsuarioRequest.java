package edu.technosplay.NextClass.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Schema(description = "Dados para criação ou atualização de um usuário")
public record UsuarioRequest(
        @Schema(description = "Nome completo do usuário", example = "João da Silva")
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome completo deve ter entre 3 e 100 caracteres")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O campo deve conter apenas letras.")
        String nome,

        @Schema(description = "CPF sem formatação, apenas 11 dígitos numéricos", example = "12345678909")
        @NotBlank(message = "CPF é obrigatório")
        //@CPF(message = "CPF inválido")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
        String cpf,

        @Schema(description = "Endereço de e-mail", example = "joao@email.com")
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
        String email,

        @Schema(description = "Senha de acesso com no mínimo 8 caracteres", example = "minhasenha123")
        @NotBlank(message = "Senha é obrigatório")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String senha,

        @Schema(description = "Telefone de contato sem formatação, apenas os 11 dígitos numéricos", example = "71987654321")
        @NotBlank(message = "Telefone é obrigatório")
        @Pattern(regexp = "^\\d{11}", message = "Telefone inválido. Use somente valores numéricos")
        String telefone,

        @Schema(description = "Data de nascimento no formato dd/MM/yyyy", example = "15/03/2000")
        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento inválida")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

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

        @Schema(description = "CEP sem formatação, apenas 8 dígitos numéricos", example = "40000000")
        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
        String cep,

        @Schema(description = "Papel do usuário no sistema",
                example = "PROFESSOR",
                allowableValues = {"ALUNO", "PROFESSOR", "COORDENADOR"}
        )
        @NotNull(message = "Role é obrigatória")
        Role role

) {
}
