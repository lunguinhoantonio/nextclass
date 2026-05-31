package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.AtendimentoRequest;
import edu.technosplay.NextClass.dto.response.AtendimentoResponse;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;

import java.util.List;

public interface AtendimentoService {
    AtendimentoResponse abrirPublico(AtendimentoRequest request);
    AtendimentoResponse abrir(Long solicitanteId, AtendimentoRequest request);
    AtendimentoResponse buscarPorId(Long id);
    List<AtendimentoResponse> listarPorSolicitante(Long solicitanteId);
    List<AtendimentoResponse> listarPorAtendente(Long atendenteId);
    List<AtendimentoResponse> listarSemAtendente();
    List<AtendimentoResponse> listarPorStatus(StatusAtendimento status);
    List<AtendimentoResponse> listarPorTipo(TipoAtendimento tipo);
    List<AtendimentoResponse> listarPorTipoEStatus(TipoAtendimento tipo, StatusAtendimento status);
    AtendimentoResponse atribuirAtendente(Long atendimentoId, Long atendenteId);
    AtendimentoResponse atualizarStatus(Long id, StatusAtendimento novoStatus);
}
