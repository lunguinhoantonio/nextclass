package edu.technosplay.NextClass.mapper;

import edu.technosplay.NextClass.dto.request.UsuarioRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.model.Usuario;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UsuarioMapper {
    public static Usuario toEntity(UsuarioRequest request) {
        return Usuario.builder()
                .nome(request.nome())
                .cpf(request.cpf())
                .email(request.email())
                .telefone(request.telefone())
                .dataNascimento(request.dataNascimento())
                .logradouro(request.logradouro())
                .complemento(request.complemento())
                .numero(request.numero())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .estado(request.estado())
                .cep(request.cep())
                .build();
    }

    public static UsuarioResponse toResponse(Usuario usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .cpf(usuario.getCpf())
                .email(usuario.getEmail())
                .telefone(usuario.getTelefone())
                .dataNascimento(usuario.getDataNascimento())
                .role(usuario.getRole())
                .ativo(usuario.isAtivo())
                .logradouro(usuario.getLogradouro())
                .numero(usuario.getNumero())
                .complemento(usuario.getComplemento())
                .bairro(usuario.getBairro())
                .cidade(usuario.getCidade())
                .estado(usuario.getEstado())
                .cep(usuario.getCep())
                .criadoEm(usuario.getCriadoEm())
                .atualizadoEm(usuario.getAtualizadoEm())
                .build();
    }
}
