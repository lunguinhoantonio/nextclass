package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.UsuarioRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.UsuarioMapper;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.repository.UsuarioRepository;
import edu.technosplay.NextClass.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UsuarioResponse registrar(UsuarioRequest request) {
        validarEmailUnico(request.email());
        validarCpfUnico(request.cpf());

        Usuario usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .cpf(request.cpf())
                .telefone(request.telefone())
                .dataNascimento(request.dataNascimento())
                .role(request.role())
                .ativo(true)
                .logradouro(request.logradouro())
                .numero(request.numero())
                .complemento(request.complemento())
                .bairro(request.bairro())
                .cidade(request.cidade())
                .estado(request.estado())
                .cep(request.cep())
                .build();

        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    private void validarCpfUnico(String cpf) {
        if (usuarioRepository.existsByCpf(cpf)) {
            throw new RegraDeNegocioException("CPF já cadastrado: " + cpf);
        }
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RegraDeNegocioException("E-mail já cadastrado: " + email);
        }
    }
}
