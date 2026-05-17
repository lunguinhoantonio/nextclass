package edu.technosplay.NextClass.controller;


import edu.technosplay.NextClass.dto.request.UsuarioRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nextclass/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Cadastro de usuários")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/registrar")
    @Operation(
            summary = "Cadastrar usuário",
            description = "Registra um novo usuário no sistema. " +
                    "Todos os campos obrigatórios devem ser preenchidos. " +
                    "A data de nascimento deve estar no formato dd/MM/yyyy."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou campos obrigatórios ausentes"
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "CPF ou e-mail já cadastrado"
            )
    })
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }
}
