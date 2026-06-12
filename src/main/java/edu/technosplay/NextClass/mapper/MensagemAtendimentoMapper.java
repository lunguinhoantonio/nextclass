package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.response.MensagemAtendimentoResponse;
import edu.technosplay.NextClass.model.MensagemAtendimento;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MensagemAtendimentoMapper {
    public MensagemAtendimentoResponse toResponse(MensagemAtendimento mensagem) {
        return MensagemAtendimentoResponse.builder()
                .id(mensagem.getId())
                .nomeRemetente(mensagem.getNomeRemetente())
                .conteudo(mensagem.getConteudo())
                .tipoRemetente(mensagem.getTipoRemetente())
                .enviadoEm(mensagem.getEnviadoEm())
                .build();
    }
}
