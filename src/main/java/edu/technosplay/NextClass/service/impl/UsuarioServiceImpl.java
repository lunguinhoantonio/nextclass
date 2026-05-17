package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.AlunoRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.exception.RecursoDuplicadoException;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.AlunoMapper;
import edu.technosplay.NextClass.model.Aluno;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import edu.technosplay.NextClass.repository.AlunoRepository;
import edu.technosplay.NextClass.service.AlunoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Slf4j
public class AlunoServiceImpl implements AlunoService {
    private final AlunoRepository alunoRepository;

    @Override
    @Transactional
    public UsuarioResponse criar(AlunoRequest request) {
        log.info("Criando aluno com CPF: {}", request.cpf());

        validarCpfUnico(request.cpf(), null);
        validarEmailUnico(request.email(), null);

        Aluno aluno = Aluno.builder()
                .nome(request.nome())
                .cpf(limparCpf(request.cpf()))
                .email(request.email().toLowerCase())
                .senha(request.senha())
                .telefone(request.telefone())
                .dataNascimento(request.dataNascimento())
                .endereco(request.endereco())
                .build();

        Aluno salvo = alunoRepository.save(aluno);
        log.info("Aluno criado com sucesso. ID: {}", salvo.getId());

        return AlunoMapper.toResponse(salvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return AlunoMapper.toResponse(encontrarPorId(id));
    }

    @Override
    public UsuarioResponse atualizar(Long id, AlunoRequest request) {
        log.info("Atualizando aluno ID: {}", id);

        Aluno aluno = encontrarPorId(id);
        validarCpfUnico(request.cpf(), id);
        validarEmailUnico(request.email(), id);

        aluno.setNome(request.nome());
        aluno.setCpf(limparCpf(request.cpf()));
        aluno.setEmail(request.email().toLowerCase());
        aluno.setSenha(request.senha());
        aluno.setTelefone(request.telefone());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setEndereco(request.endereco());

        return AlunoMapper.toResponse(alunoRepository.save(aluno));
    }

    @Override
    public void inativar(Long id) {
        log.info("Inativando aluno ID: {}", id);
        Aluno aluno = encontrarPorId(id);

        if (aluno.getStatus() == StatusAluno.INATIVO) {
            throw new RegraDeNegocioException("Aluno já está inativo");
        }

        aluno.setStatus(StatusAluno.INATIVO);
        alunoRepository.save(aluno);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listar(Pageable pageable) {
        return alunoRepository.findAll(pageable).map(AlunoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> listarPorStatus(StatusAluno status, Pageable pageable) {
        return alunoRepository.findByStatus(status, pageable).map(AlunoMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuarioResponse> buscarPorTermo(String termo, Pageable pageable) {
        return alunoRepository.buscarPorTermo(termo, pageable).map(AlunoMapper::toResponse);
    }

    private Aluno encontrarPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno", id));
    }

    private void validarCpfUnico(String cpf, Long idExcluido) {
        String cpfLimpo = limparCpf(cpf);
        alunoRepository.findByCpf(cpfLimpo).ifPresent(aluno -> {
            if (!aluno.getId().equals(idExcluido)) {
                throw new RecursoDuplicadoException("CPF já cadastrado: " + cpf);
            }
        });
    }

    private void validarEmailUnico(String email, Long idExcluido) {
        alunoRepository.findByEmail(email.toLowerCase()).ifPresent(aluno -> {
            if (!aluno.getId().equals(idExcluido)) {
                throw new RecursoDuplicadoException("E-mail já cadastrado: " + email);
            }
        });
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[.\\-]", "");
    }

    private String formatarCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) return cpf;
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." +
                cpf.substring(6, 9) + "-" + cpf.substring(9);
    }


}
