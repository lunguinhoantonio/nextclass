package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.MatriculaRequest;
import edu.technosplay.NextClass.dto.response.MatriculaResponse;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.MatriculaMapper;
import edu.technosplay.NextClass.model.Matricula;
import edu.technosplay.NextClass.model.Turma;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.StatusMatricula;
import edu.technosplay.NextClass.repository.MatriculaRepository;
import edu.technosplay.NextClass.repository.TurmaRepository;
import edu.technosplay.NextClass.repository.UsuarioRepository;
import edu.technosplay.NextClass.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatriculaServiceImpl implements MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final TurmaRepository turmaRepository;

    @Override
    @Transactional
    public MatriculaResponse matricular(Long alunoId, MatriculaRequest request) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário", alunoId));

        Turma turma = turmaRepository.findById(request.turmaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma", request.turmaId()));

        if (!turma.isAtiva()) {
            throw new RegraDeNegocioException("Não é possível se matricular em uma turma inativa.");
        }

        if (matriculaRepository.alunoJaMatriculadoNaTurma(alunoId, turma.getId())) {
            throw new RegraDeNegocioException("Aluno já está matriculado nesta turma.");
        }

        int vagasOcupadas = turmaRepository.contarMatriculasAtivas(turma.getId());
        if (turma.getCurso().getQtdVagas() != null && vagasOcupadas >= turma.getCurso().getQtdVagas()) {
            throw new RegraDeNegocioException("Turma sem vagas disponíveis.");
        }

        Matricula matricula = Matricula.builder()
                .aluno(aluno)
                .turma(turma)
                .status(StatusMatricula.ATIVA)
                .build();

        matriculaRepository.save(matricula);

        int vagasAposMatricula = vagasOcupadas + 1;
        log.info("Aluno {} matriculado na turma {}", alunoId, turma.getId());
        return MatriculaMapper.toResponse(matricula, vagasAposMatricula);
    }

    @Override
    @Transactional
    public MatriculaResponse cancelar(Long matriculaId, Long alunoId) {
        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula", matriculaId));

        if (!matricula.getAluno().getId().equals(alunoId)) {
            throw new RegraDeNegocioException("Não autorizado a cancelar esta matrícula.");
        }

        if (matricula.getStatus() != StatusMatricula.ATIVA) {
            throw new RegraDeNegocioException("Somente matrículas ativas podem ser canceladas.");
        }

        matricula.setStatus(StatusMatricula.CANCELADA);
        int vagasOcupadas = turmaRepository.contarMatriculasAtivas(matricula.getTurma().getId());
        log.info("Matrícula {} cancelada pelo aluno {}", matriculaId, alunoId);
        return MatriculaMapper.toResponse(matricula, vagasOcupadas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponse> listarPorAluno(Long alunoId) {
        if (!usuarioRepository.existsById(alunoId)) {
            throw new RecursoNaoEncontradoException("Usuário", alunoId);
        }
        return matriculaRepository.findAllByAlunoId(alunoId).stream()
                .map(m -> MatriculaMapper.toResponse(m, turmaRepository.contarMatriculasAtivas(m.getTurma().getId())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponse buscarPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Matrícula", id));
        int vagasOcupadas = turmaRepository.contarMatriculasAtivas(matricula.getTurma().getId());
        return MatriculaMapper.toResponse(matricula, vagasOcupadas);
    }
}
