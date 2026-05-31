package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.AtendimentoResponse;
import edu.technosplay.NextClass.model.Atendimento;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AtendimentoMapper {
    public AtendimentoResponse toResponse(Atendimento atendimento) {
        return AtendimentoResponse.builder()
                .id(atendimento.getId())
                .nomeCompleto(atendimento.getNomeCompleto())
                .telefone(atendimento.getTelefone())
                .email(atendimento.getEmail())
                .solicitante(atendimento.getSolicitante() != null ? UsuarioMapper.toResponse(atendimento.getSolicitante()) : null)
                .atendente(atendimento.getAtendente() != null ? UsuarioMapper.toResponse(atendimento.getAtendente()) : null)
                .tipo(atendimento.getTipo())
                .tipoLabel(atendimento.getTipo().getLabel())
                .assunto(atendimento.getAssunto())
                .descricao(atendimento.getDescricao())
                .dataAgendamento(atendimento.getDataAgendamento())
                .dataRealizacao(atendimento.getDataRealizacao())
                .status(atendimento.getStatus())
                .statusLabel(atendimento.getStatus().getLabel())
                .criadoEm(atendimento.getCriadoEm())
                .build();
    }
}
