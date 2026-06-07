package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.TurmaRequest;
import edu.technosplay.NextClass.dto.response.TurmaResponse;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.TurmaMapper;
import edu.technosplay.NextClass.model.Curso;
import edu.technosplay.NextClass.model.Turma;
import edu.technosplay.NextClass.repository.CursoRepository;
import edu.technosplay.NextClass.repository.TurmaRepository;
import edu.technosplay.NextClass.service.TurmaService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaServiceImpl implements TurmaService {
    private final TurmaRepository turmaRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional
    public TurmaResponse criar(TurmaRequest request) {
        Curso curso = cursoRepository.findById(request.cursoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado com id: " + request.cursoId()));

        if (!curso.isAtivo()) {
            throw new RegraDeNegocioException("Não é possível criar turma para um curso inativo.");
        }

        Turma turma = Turma.builder()
                .codigo(request.codigo())
                .curso(curso)
                .ativa(true)
                .build();

        turmaRepository.save(turma);
        return TurmaMapper.toResponse(turma, 0);
    }

    @Override
    @Transactional(readOnly = true)
    public TurmaResponse buscarPorId(Long id) {
        Turma turma = findById(id);
        int ocupadas = turmaRepository.contarMatriculasAtivas(turma.getId());
        return TurmaMapper.toResponse(turma, ocupadas);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TurmaResponse> listar(Long cursoId, Boolean ativa) {
        if (cursoId != null && !cursoRepository.existsById(cursoId)) {
            throw new RecursoNaoEncontradoException("Curso não encontrado com id: " + cursoId);
        }
        return turmaRepository.listar(cursoId, ativa).stream()
                .map(t -> TurmaMapper.toResponse(t, turmaRepository.contarMatriculasAtivas(t.getId())))
                .toList();
    }

    @Override
    @Transactional
    public TurmaResponse ativar(Long id) {
        Turma turma = findById(id);
        if (turma.isAtiva()) {
            throw new RegraDeNegocioException("Turma já está ativa.");
        }
        turma.setAtiva(true);
        turmaRepository.save(turma);
        int ocupadas = turmaRepository.contarMatriculasAtivas(turma.getId());
        return TurmaMapper.toResponse(turma, ocupadas);
    }

    @Override
    @Transactional
    public TurmaResponse desativar(Long id) {
        Turma turma = findById(id);
        if (!turma.isAtiva()) {
            throw new RegraDeNegocioException("Turma já está inativa.");
        }
        turma.setAtiva(false);
        turmaRepository.save(turma);
        int ocupadas = turmaRepository.contarMatriculasAtivas(turma.getId());
        return TurmaMapper.toResponse(turma, ocupadas);
    }

    private Turma findById(Long id) {
        return turmaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turma não encontrada com id: " + id));
    }
}
