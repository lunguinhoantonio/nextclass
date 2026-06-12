package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.AtendimentoAutenticadoRequest;
import edu.technosplay.NextClass.dto.request.AtendimentoRequest;
import edu.technosplay.NextClass.dto.response.AtendimentoResponse;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AtendimentoService {
    AtendimentoResponse abrirPublico(AtendimentoRequest request);
    AtendimentoResponse abrirAutenticado(AtendimentoAutenticadoRequest request, Usuario usuario);
    AtendimentoResponse abrir(Long solicitanteId, AtendimentoRequest request);
    AtendimentoResponse buscarPorId(Long id);
    List<AtendimentoResponse> listar(TipoAtendimento tipo, StatusAtendimento status, Sort sort);
    List<AtendimentoResponse> listarPorSolicitante(Long solicitanteId);
    List<AtendimentoResponse> listarPorAtendente(Long atendenteId);
    List<AtendimentoResponse> listarSemAtendente();
    AtendimentoResponse atribuirAtendente(Long atendimentoId, Long atendenteId);
    AtendimentoResponse atualizarStatus(Long id, StatusAtendimento novoStatus);
}
