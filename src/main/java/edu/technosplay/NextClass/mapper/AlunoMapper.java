package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.request.AlunoRequest;
import edu.technosplay.NextClass.dto.response.AlunoResponse;
import edu.technosplay.NextClass.model.Aluno;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AlunoMapper {
    public static Aluno toAluno(AlunoRequest request) {
        return Aluno.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .email(request.email())
                .telefone(request.telefone())
                .dataNascimento(request.dataNascimento())
                .endereco(request.endereco())
                .build();
    }

    public static AlunoResponse toResponse(Aluno aluno) {
        return AlunoResponse.builder()
                .id(aluno.getId())
                .nome(aluno.getNome())
                .cpf(aluno.getCpf())
                .email(aluno.getEmail())
                .telefone(aluno.getTelefone())
                .dataNascimento(aluno.getDataNascimento())
                .status(aluno.getStatus())
                .criadoEm(aluno.getCriadoEm())
                .build();
    }
}
