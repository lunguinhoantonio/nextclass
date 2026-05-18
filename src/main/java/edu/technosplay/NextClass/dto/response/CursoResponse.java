package edu.technosplay.NextClass.dto.response;

import lombok.Builder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
public record CursoResponse(
        Long id,
        String nome,
        Integer qtdVagas,
        LocalTime horarioInicio,
        LocalTime horarioFim,
        DayOfWeek diaInicio,
        DayOfWeek diaFim,
        boolean ativo,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
}
