package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.TurmaRequest;
import edu.technosplay.NextClass.dto.response.TurmaResponse;
import edu.technosplay.NextClass.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nextclass/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Gerenciamento de turmas")
public class TurmaController {
    private final TurmaService turmaService;

    @PostMapping
    @PreAuthorize("hasRole('COORDENADOR')")
    @Operation(summary = "Criar turma", description = "Cria uma nova turma vinculada a um curso. Requer perfil COORDENADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Turma criada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    public ResponseEntity<TurmaResponse> criar(@RequestBody @Valid TurmaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(turmaService.criar(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar turma por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turma encontrada"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    })
    public ResponseEntity<TurmaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.buscarPorId(id));
    }

    @GetMapping
    @Operation(
            summary = "Listar turmas",
            description = "Lista turmas com filtros opcionais de curso e/ou status. Todos os parâmetros são opcionais e podem ser combinados."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<TurmaResponse>> listar(
            @Parameter(description = "Filtrar por ID do curso")
            @RequestParam(required = false) Long cursoId,
            @Parameter(description = "Filtrar por status: true = ativas, false = inativas")
            @RequestParam(required = false) Boolean ativa) {
        return ResponseEntity.ok(turmaService.listar(cursoId, ativa));
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasRole('COORDENADOR')")
    @Operation(summary = "Ativar turma", description = "Ativa uma turma inativa. Requer perfil COORDENADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turma ativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "422", description = "Turma já está ativa")
    })
    public ResponseEntity<TurmaResponse> ativar(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.ativar(id));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('COORDENADOR')")
    @Operation(summary = "Desativar turma", description = "Desativa uma turma ativa. Requer perfil COORDENADOR.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Turma desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "422", description = "Turma já está inativa")
    })
    public ResponseEntity<TurmaResponse> desativar(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.desativar(id));
    }
}
