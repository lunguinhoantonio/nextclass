package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.AlunoRequest;
import edu.technosplay.NextClass.dto.response.AlunoResponse;
import edu.technosplay.NextClass.dto.response.PageResponse;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import edu.technosplay.NextClass.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nextclass/alunos")
@RequiredArgsConstructor
public class AlunoController {
    private final AlunoService service;

    @PostMapping
    @Operation(summary = "Cadastrar aluno", description = "Realiza o cadastro de um novo aluno")
    @ApiResponse(responseCode = "201", description = "Aluno cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "409", description = "CPF ou e-mail já cadastrado")
    public ResponseEntity<AlunoResponse> criar(@RequestBody @Valid AlunoRequest request) {
        AlunoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno por ID")
    @ApiResponse(responseCode = "200", description = "Aluno encontrado")
    @ApiResponse(responseCode = "404", description = "Aluno não encontrado")
    public ResponseEntity<AlunoResponse> buscarPorId(
            @Parameter(description = "ID do aluno") @PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno")
    @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso")
    public ResponseEntity<AlunoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AlunoRequest request) {
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inativar aluno")
    @ApiResponse(responseCode = "204", description = "Aluno inativado com sucesso")
    public ResponseEntity<Void> inativar(@PathVariable Long id) {
        service.inativar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos")
    public ResponseEntity<PageResponse<AlunoResponse>> listar(
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(PageResponse.de(service.listar(pageable)));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Listar alunos por status")
    public ResponseEntity<PageResponse<AlunoResponse>> listarPorStatus(
            @PathVariable StatusAluno status,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(PageResponse.de(service.listar(pageable)));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar alunos por nome, CPF ou e-mail")
    public ResponseEntity<PageResponse<AlunoResponse>> buscar(
            @Parameter(description = "Termo de busca") @RequestParam String termo,
            @PageableDefault(size = 20, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(PageResponse.de(service.listar(pageable)));
    }
}
