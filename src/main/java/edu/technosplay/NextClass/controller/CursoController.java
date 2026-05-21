package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.CursoPatchRequest;
import edu.technosplay.NextClass.dto.request.CursoRequest;
import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.service.CursoService;
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
@RequestMapping("/nextclass/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "Gerenciamento de cursos")
public class CursoController {
    private final CursoService cursoService;

    @GetMapping
    @Operation(
            summary = "Listar cursos",
            description = "Lista todos os cursos com filtros opcionais de professor e/ou status. " +
                    "Todos os parâmetros são opcionais e podem ser combinados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    public ResponseEntity<List<CursoResponse>> listar(
            @Parameter(description = "Filtrar por professor", example = "1")
            @RequestParam(required = false) Long professorId,
            @Parameter(description = "Filtrar por status: cursos ativos = true, cursos inativos = false", example = "true")
            @RequestParam(required = false) Boolean ativo) {
        return ResponseEntity.ok(cursoService.listar(professorId, ativo));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar curso por ID",
            description = "Retorna os dados de um curso específico pelo seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso encontrado"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    public ResponseEntity<CursoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.listarPorId(id));
    }

    @PostMapping
    @Operation(
            summary = "Criar curso",
            description = "Cria um novo curso. O professor é opcional no momento da criação."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Professor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponse> criar(@Valid @RequestBody CursoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.criar(request));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar curso",
            description = "Atualiza todos os dados de um curso existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso ou professor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponse> atualizar(@PathVariable Long id, @Valid @RequestBody CursoRequest request) {
        return ResponseEntity.ok(cursoService.atualizar(id, request));
    }

    @PatchMapping("/{id}")
    @Operation(
            summary = "Atualizar curso parcialmente",
            description = "Atualiza apenas os campos informados de um curso. Todos os campos são opcionais."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Curso ou professor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponse> atualizarPatch(
            @Parameter(description = "ID do curso", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody CursoPatchRequest request) {
        return ResponseEntity.ok(cursoService.atualizarPatch(id, request));
    }

    @PatchMapping("/{id}/desativar")
    @Operation(
            summary = "Desativar curso",
            description = "Desativa um curso, impedindo novas matrículas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponse> desativar(
            @Parameter(description = "ID do curso", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(cursoService.desativar(id));
    }

    @PatchMapping("/{id}/ativar")
    @Operation(
            summary = "Ativar curso",
            description = "Reativa um curso previamente desativado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Curso não encontrado")
    })
    //@PreAuthorize("hasRole('COORDENADOR')")
    public ResponseEntity<CursoResponse> ativar(
            @Parameter(description = "ID do curso", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(cursoService.ativar(id));
    }
}
