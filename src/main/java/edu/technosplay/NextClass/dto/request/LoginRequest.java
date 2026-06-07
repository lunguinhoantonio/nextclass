package edu.technosplay.NextClass.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais para login")
public record LoginRequest(
        @Schema(description = "E-mail do usuário", example = "joao@email.com")
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "minhasenha123")
        @NotBlank(message = "Senha é obrigatória")
        String senha
) {
}
