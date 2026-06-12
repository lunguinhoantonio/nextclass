package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.request.MensagemAtendimentoRequest;
import edu.technosplay.NextClass.dto.response.MensagemAtendimentoResponse;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.service.MensagemAtendimentoService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nextclass/atendimentos/{atendimentoId}/mensagens")
@RequiredArgsConstructor
@Tag(name = "Mensagens de Atendimento", description = "Chat vinculado a um atendimento")
public class MensagemAtendimentoController {

    private final MensagemAtendimentoService mensagemAtendimentoService;

    @GetMapping
    @Operation(
            summary = "Listar mensagens",
            description = "Retorna o histórico de mensagens de um atendimento em ordem cronológica. Acesso público."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado")
    })
    public ResponseEntity<List<MensagemAtendimentoResponse>> listar(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long atendimentoId) {
        return ResponseEntity.ok(mensagemAtendimentoService.listar(atendimentoId));
    }

    @PostMapping("/atendente")
    @PreAuthorize("hasAnyRole('ATENDENTE', 'COORDENADOR')")
    @Operation(
            summary = "Atendente envia mensagem",
            description = "Envia uma mensagem como atendente. Requer role ATENDENTE ou COORDENADOR."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mensagem enviada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado")
    })
    public ResponseEntity<MensagemAtendimentoResponse> enviarComoAtendente(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long atendimentoId,
            @Valid @RequestBody MensagemAtendimentoRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemAtendimentoService.enviarComoAtendente(atendimentoId, request, usuario));
    }

    @PostMapping("/solicitante")
    @Operation(
            summary = "Solicitante envia mensagem",
            description = "Envia uma mensagem como solicitante. Acesso público. " +
                    "Se não autenticado, o campo 'nomeRemetente' é obrigatório no body."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mensagem enviada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Atendimento não encontrado"),
            @ApiResponse(responseCode = "422", description = "nomeRemetente ausente para solicitante anônimo")
    })
    public ResponseEntity<MensagemAtendimentoResponse> enviarComoSolicitante(
            @Parameter(description = "ID do atendimento", example = "1")
            @PathVariable Long atendimentoId,
            @Valid @RequestBody MensagemAtendimentoRequest request,
            @AuthenticationPrincipal Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mensagemAtendimentoService.enviarComoSolicitante(atendimentoId, request, usuario));
    }
}