package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.AtendimentoRequest;
import edu.technosplay.NextClass.dto.response.AtendimentoResponse;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import edu.technosplay.NextClass.service.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nextclass/atendimentos")
@RequiredArgsConstructor
@Tag(name = "Atendimentos", description = "Agendamento e gestão de atendimentos")
public class AtendimentoController {
    private final AtendimentoService atendimentoService;

    @PostMapping("/publico")
    @Operation(
            summary = "Abrir atendimento (público)",
            description = "Permite que qualquer pessoa, sem conta no sistema, agende um atendimento."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Agendamento realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada")
    })
    public ResponseEntity<AtendimentoResponse> abrirPublico(
            @Valid @RequestBody AtendimentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(atendimentoService.abrirPublico(request));
    }

    @PostMapping("/solicitante/{solicitanteId}")
    @Operation(
            summary = "Abrir atendimento",
            description = "Cria um novo agendamento de atendimento para o solicitante informado. " +
                    "O campo 'atendenteId' é opcional; se informado, deve ser um usuário com a role ATENDENTE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Atendimento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Solicitante ou atendente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Regra de negócio violada (ex: atendente com role inválida)")
    })
    public ResponseEntity<AtendimentoResponse> abrir(
            @Parameter(description = "ID do usuário solicitante", example = "1")
            @PathVariable Long solicitanteId,
            @Valid @RequestBody AtendimentoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(atendimentoService.abrir(solicitanteId, request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar atendimento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atendimento encontrado"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado")
    })
    public ResponseEntity<AtendimentoResponse> buscarPorId(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(atendimentoService.buscarPorId(id));
    }

    @GetMapping("/solicitante/{solicitanteId}")
    @Operation(summary = "Listar atendimentos por solicitante",
            description = "Retorna todos os atendimentos abertos pelo solicitante, em ordem decrescente de data.")
    public ResponseEntity<List<AtendimentoResponse>> listarPorSolicitante(
            @Parameter(description = "ID do solicitante", example = "1")
            @PathVariable Long solicitanteId) {
        return ResponseEntity.ok(atendimentoService.listarPorSolicitante(solicitanteId));
    }

    @GetMapping("/atendente/{atendenteId}")
    @Operation(summary = "Listar atendimentos de um atendente",
            description = "Retorna todos os atendimentos atribuídos ao atendente, em ordem crescente de data.")
    public ResponseEntity<List<AtendimentoResponse>> listarPorAtendente(
            @Parameter(description = "ID do atendente (deve ter role ATENDENTE)", example = "2")
            @PathVariable Long atendenteId) {
        return ResponseEntity.ok(atendimentoService.listarPorAtendente(atendenteId));
    }

    @GetMapping("/sem-atendente")
    @Operation(summary = "Listar atendimentos sem atendente",
            description = "Retorna atendimentos que ainda não foram atribuídos a nenhum atendente.")
    public ResponseEntity<List<AtendimentoResponse>> listarSemAtendente() {
        return ResponseEntity.ok(atendimentoService.listarSemAtendente());
    }

    @GetMapping
    @Operation(
            summary = "Listar atendimentos com filtros",
            description = "Lista atendimentos filtrando por tipo e/ou status. Todos os parâmetros são opcionais."
    )
    public ResponseEntity<List<AtendimentoResponse>> listar(
            @Parameter(description = "Filtrar por tipo: SUPORTE, ACADEMICO, FINANCEIRO, OUTRO")
            @RequestParam(required = false) TipoAtendimento tipo,
            @Parameter(description = "Filtrar por status: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO")
            @RequestParam(required = false) StatusAtendimento status) {

        if (tipo != null && status != null) {
            return ResponseEntity.ok(atendimentoService.listarPorTipoEStatus(tipo, status));
        } else if (tipo != null) {
            return ResponseEntity.ok(atendimentoService.listarPorTipo(tipo));
        } else if (status != null) {
            return ResponseEntity.ok(atendimentoService.listarPorStatus(status));
        }

        return ResponseEntity.ok(atendimentoService.listarPorStatus(StatusAtendimento.AGENDADO));
    }

    @PatchMapping("/{atendimentoId}/atribuir-atendente/{atendenteId}")
    @Operation(
            summary = "Atribuir atendente",
            description = "Atribui ou reatribui um atendente ao atendimento. " +
                    "O usuário informado DEVE ter a role ATENDENTE; qualquer outra role será rejeitada com 422."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Atendente atribuído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atendimento ou atendente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Atendimento finalizado ou atendente com role inválida")
    })
    public ResponseEntity<AtendimentoResponse> atribuirAtendente(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long atendimentoId,
            @Parameter(description = "ID do usuário atendente (role ATENDENTE obrigatória)", example = "2")
            @PathVariable Long atendenteId) {
        return ResponseEntity.ok(atendimentoService.atribuirAtendente(atendimentoId, atendenteId));
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Atualizar status do atendimento",
            description = "Atualiza o andamento do atendimento. Não é possível alterar um atendimento " +
                    "já REALIZADO ou CANCELADO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "422", description = "Status inválido para o estado atual")
    })
    public ResponseEntity<AtendimentoResponse> atualizarStatus(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Novo status: AGENDADO, CONFIRMADO, REALIZADO, CANCELADO")
            @RequestParam StatusAtendimento status) {
        return ResponseEntity.ok(atendimentoService.atualizarStatus(id, status));
    }
}
