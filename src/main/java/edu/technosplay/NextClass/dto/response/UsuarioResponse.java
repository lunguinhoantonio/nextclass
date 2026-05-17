package edu.technosplay.NextClass.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record AlunoResponse(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        StatusAluno status,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime criadoEm,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime atualizadoEm
) {
}
