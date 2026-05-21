package edu.technosplay.NextClass.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Resposta paginada genérica")
public record PageResponse<T>(
        @Schema(description = "Lista de itens da página atual")
        List<T> conteudo,

        @Schema(description = "Número da página atual (começa em 0)", example = "0")
        int pagina,

        @Schema(description = "Quantidade de itens por página", example = "10")
        int tamanhoPagina,

        @Schema(description = "Total de elementos em todas as páginas", example = "42")
        long totalElementos,

        @Schema(description = "Total de páginas", example = "5")
        int totalPaginas,

        @Schema(description = "Indica se é a primeira página", example = "true")
        boolean primeira,

        @Schema(description = "Indica se é a última página", example = "false")
        boolean ultima
) {
    public static <T> PageResponse<T> de(Page<T> page) {
        return new PageResponse<>(
          page.getContent(),
          page.getNumber(),
          page.getSize(),
          page.getTotalElements(),
          page.getTotalPages(),
          page.isFirst(),
          page.isLast()
        );
    }
}
