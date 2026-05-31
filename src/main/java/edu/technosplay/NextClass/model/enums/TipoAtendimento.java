package edu.technosplay.NextClass.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoAtendimento {
    SUPORTE("Suporte Técnico", "Problemas com sistema, acesso e plataforma"),
    ACADEMICO("Acadêmico", "Dúvidas sobre disciplinas, notas e conteúdo"),
    FINANCEIRO("Financeiro", "Boletos, mensalidades e condições de pagamento"),
    OUTRO("Outro", "Demandas não enquadradas nas categorias anteriores");

    private final String label;
    private final String descricao;
}
