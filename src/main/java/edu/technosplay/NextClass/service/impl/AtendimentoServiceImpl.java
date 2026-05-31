package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.AtendimentoRequest;
import edu.technosplay.NextClass.dto.response.AtendimentoResponse;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.AtendimentoMapper;
import edu.technosplay.NextClass.model.Atendimento;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.Role;
import edu.technosplay.NextClass.model.enums.StatusAtendimento;
import edu.technosplay.NextClass.model.enums.TipoAtendimento;
import edu.technosplay.NextClass.repository.AtendimentoRepository;
import edu.technosplay.NextClass.repository.UsuarioRepository;
import edu.technosplay.NextClass.service.AtendimentoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AtendimentoServiceImpl implements AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public AtendimentoResponse abrirPublico(AtendimentoRequest request) {
        Usuario atendente = null;
        if (request.atendenteId() != null) {
            atendente = buscarUsuario(request.atendenteId());
            validarAtendente(atendente);
        }

        Atendimento atendimento = Atendimento.builder()
                .solicitante(null)
                .atendente(atendente)
                .nomeCompleto(request.nomeCompleto())
                .cpf(request.cpf())
                .dataNascimento(request.dataNascimento().atStartOfDay())
                .email(request.email())
                .telefone(request.telefone())
                .tipo(request.tipo())
                .assunto(request.assunto())
                .descricao(request.descricao())
                .dataAgendamento(request.dataAgendamento())
                .horaAgendamento(request.dataAgendamento().toLocalTime())
                .build();

        Atendimento salvo = atendimentoRepository.save(atendimento);
        log.info("[ATENDIMENTO-PUBLICO] Aberto por visitante | tipo={} | assunto={}",
                request.tipo(), request.assunto());

        return AtendimentoMapper.toResponse(salvo);
    }

    @Override
    @Transactional
    public AtendimentoResponse abrir(Long solicitanteId, AtendimentoRequest request) {
        Usuario solicitante = buscarUsuario(solicitanteId);

        Usuario atendente = null;
        if (request.atendenteId() != null) {
            atendente = buscarUsuario(request.atendenteId());
            validarAtendente(atendente);
        }

        Atendimento atendimento = Atendimento.builder()
                .solicitante(solicitante)
                .atendente(atendente)
                .nomeCompleto(request.nomeCompleto())
                .cpf(request.cpf())
                .dataNascimento(request.dataNascimento().atStartOfDay())
                .email(request.email())
                .telefone(request.telefone())
                .tipo(request.tipo())
                .assunto(request.assunto())
                .descricao(request.descricao())
                .dataAgendamento(request.dataAgendamento())
                .horaAgendamento(request.dataAgendamento().toLocalTime())
                .build();

        Atendimento salvo = atendimentoRepository.save(atendimento);
        log.info("[ATENDIMENTO] Aberto por usuário {} | tipo={} | assunto={}",
                solicitanteId, request.tipo(), request.assunto());

        return AtendimentoMapper.toResponse(salvo);
    }

    @Override
    public AtendimentoResponse buscarPorId(Long id) {
        return AtendimentoMapper.toResponse(buscarEntidade(id));
    }

    @Override
    public List<AtendimentoResponse> listarPorSolicitante(Long solicitanteId) {
        return atendimentoRepository
                .findAllBySolicitanteIdOrderByDataAgendamentoDesc(solicitanteId)
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    public List<AtendimentoResponse> listarPorAtendente(Long atendenteId) {
        return atendimentoRepository
                .findAllByAtendenteIdOrderByDataAgendamentoAsc(atendenteId)
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    public List<AtendimentoResponse> listarSemAtendente() {
        return atendimentoRepository.findAllByAtendenteIsNull()
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    public List<AtendimentoResponse> listarPorStatus(StatusAtendimento status) {
        return atendimentoRepository.findAllByStatus(status)
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    public List<AtendimentoResponse> listarPorTipo(TipoAtendimento tipo) {
        return atendimentoRepository.findAllByTipo(tipo)
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    public List<AtendimentoResponse> listarPorTipoEStatus(TipoAtendimento tipo, StatusAtendimento status) {
        return atendimentoRepository.findAllByTipoAndStatus(tipo, status)
                .stream().map(AtendimentoMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public AtendimentoResponse atribuirAtendente(Long atendimentoId, Long atendenteId) {
        Atendimento atendimento = buscarEntidade(atendimentoId);

        if (atendimento.getStatus() == StatusAtendimento.REALIZADO ||
                atendimento.getStatus() == StatusAtendimento.CANCELADO) {
            throw new RegraDeNegocioException(
                    "Não é possível alterar o atendente de um atendimento com status" +
                    atendimento.getStatus().getLabel()
            );
        }

        Usuario atendente = buscarUsuario(atendenteId);
        validarAtendente(atendente);

        atendimento.setAtendente(atendente);
        log.info("[ATENDIMENTO] id={} atribuído ao atendente id={}", atendimentoId, atendenteId);

        return AtendimentoMapper.toResponse(atendimentoRepository.save(atendimento));
    }

    @Override
    @Transactional
    public AtendimentoResponse atualizarStatus(Long id, StatusAtendimento novoStatus) {
        Atendimento atendimento = buscarEntidade(id);

        if (atendimento.getStatus() == StatusAtendimento.CANCELADO) {
            throw new RegraDeNegocioException("Atendimento cancelado não pode ser alterado");
        }
        if (atendimento.getStatus() == StatusAtendimento.REALIZADO) {
            throw new RegraDeNegocioException("Atendimento já realizado não pode ser alterado");
        }

        atendimento.setStatus(novoStatus);
        if (novoStatus == StatusAtendimento.REALIZADO) {
            atendimento.setDataRealizacao(LocalDateTime.now());
        }

        log.info("[ATENDIMENTO] id={} status atualizado para {}", id, novoStatus);
        return AtendimentoMapper.toResponse(atendimentoRepository.save(atendimento));
    }

    private Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
    }

    private Atendimento buscarEntidade(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendimento não encontrado: " + id));
    }

    private void validarAtendente(Usuario usuario) {
        if (usuario.getRole() != Role.ATENDENTE) {
            throw new RegraDeNegocioException(
                    "Usuário '" + usuario.getNome() + "' possui a role " + usuario.getRole().name()
                            + " e não pode ser atendente. Apenas usuários com a role ATENDENTE são permitidos."
            );
        }

        if (!usuario.isAtivo()) {
            throw new RegraDeNegocioException(
                    "Usuário '" + usuario.getNome() + "' está inativo e não pode receber atendimentos"
            );
        }
    }
}
