package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.MensagemAtendimentoRequest;
import edu.technosplay.NextClass.dto.response.MensagemAtendimentoResponse;
import edu.technosplay.NextClass.model.Usuario;

import java.util.List;

public interface MensagemAtendimentoService {
    List<MensagemAtendimentoResponse> listar(Long atendimentoId);
    MensagemAtendimentoResponse enviarComoAtendente(Long atendimentoId, MensagemAtendimentoRequest request, Usuario atendente);
    MensagemAtendimentoResponse enviarComoSolicitante(Long atendimentoId, MensagemAtendimentoRequest request, Usuario solicitante);
}
