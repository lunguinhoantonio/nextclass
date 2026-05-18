package edu.technosplay.NextClass.dto.request;


import java.time.DayOfWeek;
import java.time.LocalTime;

public record CursoRequest(
        String nome,
        Integer qtdVagas,
        LocalTime horarioInicio,
        LocalTime horarioFim,
        DayOfWeek diaInicio,
        DayOfWeek diaFim,
        boolean ativo
) {
}
