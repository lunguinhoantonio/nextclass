package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.MatriculaRequest;
import edu.technosplay.NextClass.dto.response.MatriculaResponse;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nextclass/matriculas")
@RequiredArgsConstructor
@Tag(name = "Matrículas", description = "Gerenciamento de matrículas de alunos em turmas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    @Operation(summary = "Matricular aluno", description = "Matricula o aluno autenticado em uma turma. Requer perfil ALUNO.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Matrícula realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    public ResponseEntity<MatriculaResponse> matricular(
            @AuthenticationPrincipal Usuario aluno,
            @RequestBody @Valid MatriculaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matriculaService.matricular(aluno.getId(), request));
    }

    @GetMapping
    @Operation(summary = "Listar matrículas do aluno autenticado")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<List<MatriculaResponse>> listarMinhas(
            @AuthenticationPrincipal Usuario aluno) {
        return ResponseEntity.ok(matriculaService.listarPorAluno(aluno.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar matrícula por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matrícula encontrada"),
            @ApiResponse(responseCode = "404", description = "Matrícula não encontrada")
    })
    public ResponseEntity<MatriculaResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar matrícula", description = "Cancela a matrícula do aluno autenticado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Matrícula cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Matrícula não encontrada"),
            @ApiResponse(responseCode = "422", description = "Matrícula não pode ser cancelada")
    })
    public ResponseEntity<MatriculaResponse> cancelar(
            @AuthenticationPrincipal Usuario aluno,
            @PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.cancelar(id, aluno.getId()));
    }
}