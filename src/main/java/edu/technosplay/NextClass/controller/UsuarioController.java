package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.dto.response.PageResponse;
import edu.technosplay.NextClass.model.enums.Role;
import edu.technosplay.NextClass.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nextclass/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuários", description = "Gerenciamento de usuários")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os dados de um usuário específico pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar usuários",
            description = "Lista todos os usuários com filtros opcionais de role e/ou status. " +
                    "Todos os parâmetros são opcionais e podem ser combinados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<List<UsuarioResponse>> listar(
            @Parameter(description = "Filtrar por role (ALUNO, PROFESSOR, COORDENADOR)")
            @RequestParam(required = false) Role role,
            @Parameter(description = "Filtrar por status: true = ativos, false = inativos")
            @RequestParam(required = false) Boolean ativo) {
        return ResponseEntity.ok(usuarioService.listar(role, ativo));
    }

    @PatchMapping("/{id}/desativar")
    @Operation(
            summary = "Desativar usuário",
            description = "Desativa a conta de um usuário pelo ID, impedindo que ele acesse o sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<UsuarioResponse> desativar(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.desativar(id));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(
            summary = "Ativar usuário",
            description = "Ativa a conta de um usuário pelo ID, permitindo que ele acesse o sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<UsuarioResponse> ativar(
            @Parameter(description = "ID do usuário", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.ativar(id));
    }
}
