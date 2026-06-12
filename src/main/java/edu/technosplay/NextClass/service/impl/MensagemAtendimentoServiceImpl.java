package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.MensagemAtendimentoRequest;
import edu.technosplay.NextClass.dto.response.MensagemAtendimentoResponse;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.MensagemAtendimentoMapper;
import edu.technosplay.NextClass.model.Atendimento;
import edu.technosplay.NextClass.model.MensagemAtendimento;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.TipoRemetente;
import edu.technosplay.NextClass.repository.AtendimentoRepository;
import edu.technosplay.NextClass.repository.MensagemAtendimentoRepository;
import edu.technosplay.NextClass.service.MensagemAtendimentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MensagemAtendimentoServiceImpl implements MensagemAtendimentoService {

    private final MensagemAtendimentoRepository mensagemRepository;
    private final AtendimentoRepository atendimentoRepository;

    @Override
    public List<MensagemAtendimentoResponse> listar(Long atendimentoId) {
        buscarAtendimento(atendimentoId);
        return mensagemRepository.findAllByAtendimentoIdOrderByEnviadoEmAsc(atendimentoId)
                .stream()
                .map(MensagemAtendimentoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MensagemAtendimentoResponse enviarComoAtendente(Long atendimentoId, MensagemAtendimentoRequest request, Usuario atendente) {
        Atendimento atendimento = buscarAtendimento(atendimentoId);

        MensagemAtendimento mensagem = MensagemAtendimento.builder()
                .atendimento(atendimento)
                .remetente(atendente)
                .nomeRemetente(atendente.getNome())
                .conteudo(request.conteudo())
                .tipoRemetente(TipoRemetente.ATENDENTE)
                .build();

        MensagemAtendimento salva = mensagemRepository.save(mensagem);
        log.info("[MENSAGEM] Atendente id={} enviou mensagem no atendimento id={}", atendente.getId(), atendimentoId);

        return MensagemAtendimentoMapper.toResponse(salva);
    }

    @Override
    @Transactional
    public MensagemAtendimentoResponse enviarComoSolicitante(Long atendimentoId, MensagemAtendimentoRequest request, Usuario solicitante) {
        Atendimento atendimento = buscarAtendimento(atendimentoId);

        String nome = solicitante != null ? solicitante.getNome() : request.nomeRemetente();
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("nomeRemetente é obrigatório para solicitantes não autenticados");
        }

        MensagemAtendimento mensagem = MensagemAtendimento.builder()
                .atendimento(atendimento)
                .remetente(solicitante)
                .nomeRemetente(nome)
                .conteudo(request.conteudo())
                .tipoRemetente(TipoRemetente.SOLICITANTE)
                .build();

        MensagemAtendimento salva = mensagemRepository.save(mensagem);
        log.info("[MENSAGEM] Solicitante '{}' enviou mensagem no atendimento id={}", nome, atendimentoId);

        return MensagemAtendimentoMapper.toResponse(salva);
    }

    private Atendimento buscarAtendimento(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendimento", id));
    }
}