package edu.technosplay.NextClass.model.enums;

import lombok.Getter;

import java.time.DayOfWeek;

public enum DiaSemana {
    SEGUNDA(DayOfWeek.MONDAY, "Segunda-feira"),
    TERCA(DayOfWeek.TUESDAY, "Terça-feira"),
    QUARTA(DayOfWeek.WEDNESDAY, "Quarta-feira"),
    QUINTA(DayOfWeek.THURSDAY, "Quinta-feira"),
    SEXTA(DayOfWeek.FRIDAY, "Sexta-feira"),
    SABADO(DayOfWeek.SATURDAY, "Sábado"),
    DOMINGO(DayOfWeek.SUNDAY, "Domingo");

    private final DayOfWeek dayOfWeek;

    @Getter
    private final String descricao;

    DiaSemana(DayOfWeek dayOfWeek, String descricao) {
        this.dayOfWeek = dayOfWeek;
        this.descricao = descricao;
    }

    public DayOfWeek toDayOfWeek() {
        return dayOfWeek;
    }
}
