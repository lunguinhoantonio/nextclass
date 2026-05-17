package edu.technosplay.NextClass.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.Role;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UsuarioRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(min = 3, max = 100, message = "Nome completo deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "CPF é obrigatório")
        //@CPF(message = "CPF inválido")
        @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
        String cpf,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "Senha é obrigatório")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres")
        String senha,

        @NotBlank(message = "Telefone é obrigatório")
        //@Pattern(regexp = "^\\d{11}", message = "Telefone inválido. Use somente valores numéricos")
        String telefone,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento inválida")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,

        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String estado,

        @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos numéricos")
        String cep,

        @NotNull(message = "Role é obrigatória")
        Role role

) {
}
