package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.request.CursoPatchRequest;
import edu.technosplay.NextClass.dto.request.CursoRequest;
import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.exception.RecursoNaoEncontradoException;
import edu.technosplay.NextClass.exception.RegraDeNegocioException;
import edu.technosplay.NextClass.mapper.CursoMapper;
import edu.technosplay.NextClass.model.Curso;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.DiaSemana;
import edu.technosplay.NextClass.repository.CursoRepository;
import edu.technosplay.NextClass.repository.UsuarioRepository;
import edu.technosplay.NextClass.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private static final DateTimeFormatter FORMATO_HORA = DateTimeFormatter.ofPattern("HH:mm");
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar(Long professorId, Boolean ativo) {
        return cursoRepository.findAll().stream()
                .filter(c -> professorId == null || Objects.equals(c.getProfessor().getId(), professorId))
                .filter(c -> ativo == null || c.isAtivo() == ativo)
                .map(CursoMapper::toResponse)
                .toList();
    }

    @Override
    public CursoResponse listarPorId(Long id) {
        return cursoRepository.findById(id)
                .map(CursoMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado: " + id));
    }

    @Override
    @Transactional
    public CursoResponse criar(CursoRequest request) {
        LocalTime inicio = LocalTime.parse(request.horarioInicio(), FORMATO_HORA);
        LocalTime fim = LocalTime.parse(request.horarioFim(), FORMATO_HORA);

        validarHorarios(inicio, fim);
        validarDias(request.diaInicio(), request.diaFim());
        Optional<Usuario> professor = procurarProfessor(request.professorId());
        Curso curso = Curso.builder()
                .nome(request.nome())
                .qtdVagas(request.qtdVagas())
                .horarioInicio(inicio)
                .horarioFim(fim)
                .diaInicio(request.diaInicio())
                .diaFim(request.diaFim())
                .professor(professor.orElse(null))
                .ativo(true)
                .build();

        return CursoMapper.toResponse(cursoRepository.save(curso));
    }

    @Override
    @Transactional
    public CursoResponse atualizar(Long id, CursoRequest request) {
        Curso curso = cursoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado: " + id));

        LocalTime inicio = LocalTime.parse(request.horarioInicio(), FORMATO_HORA);
        LocalTime fim = LocalTime.parse(request.horarioFim(), FORMATO_HORA);
        validarHorarios(inicio, fim);
        validarDias(request.diaInicio(), request.diaFim());

        curso.setNome(request.nome());
        curso.setQtdVagas(request.qtdVagas());
        curso.setHorarioInicio(inicio);
        curso.setHorarioFim(fim);
        curso.setDiaInicio(request.diaInicio());
        curso.setDiaFim(request.diaFim());
        curso.setProfessor(procurarProfessor(request.professorId()).orElse(null));

        return CursoMapper.toResponse(cursoRepository.save(curso));
    }

    @Override
    public CursoResponse atualizarPatch(Long id, CursoPatchRequest request) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado: " + id));

        Optional.ofNullable(request.nome()).ifPresent(curso::setNome);
        Optional.ofNullable(request.qtdVagas()).ifPresent(curso::setQtdVagas);
        Optional.ofNullable(request.professorId())
                .ifPresent(pid -> curso.setProfessor(procurarProfessor(pid).orElse(null)));
        DiaSemana diaInicio = Optional.ofNullable(request.diaInicio()).orElse(curso.getDiaInicio());
        DiaSemana diaFim = Optional.ofNullable(request.diaFim()).orElse(curso.getDiaFim());

        if (request.horarioInicio() != null || request.horarioFim() != null) {
            LocalTime horarioInicio = request.horarioInicio() != null
                    ? LocalTime.parse(request.horarioInicio(), FORMATO_HORA)
                    : curso.getHorarioInicio();
            LocalTime horarioFim = request.horarioFim() != null
                    ? LocalTime.parse(request.horarioFim(), FORMATO_HORA)
                    : curso.getHorarioFim();
            validarHorarios(horarioInicio, horarioFim);
            validarDias(
                    Optional.ofNullable(request.diaInicio()).orElse(curso.getDiaInicio()),
                    Optional.ofNullable(request.diaFim()).orElse(curso.getDiaFim())
            );
            curso.setDiaInicio(diaInicio);
            curso.setDiaFim(diaFim);
            curso.setHorarioInicio(horarioInicio);
            curso.setHorarioFim(horarioFim);
        }

        return CursoMapper.toResponse(cursoRepository.save(curso));
    }

    @Override
    public CursoResponse desativar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado: " + id));
        curso.setAtivo(false);
        return CursoMapper.toResponse(cursoRepository.save(curso));
    }

    @Override
    public CursoResponse ativar(Long id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Curso não encontrado: " + id));
        curso.setAtivo(true);
        return CursoMapper.toResponse(cursoRepository.save(curso));
    }

    private void validarHorarios(LocalTime inicio, LocalTime fim) {
        if (fim.isBefore(inicio)) {
            throw new RegraDeNegocioException("Horário do fim não pode ser anterior ao horário de início");
        }

        if (fim.equals(inicio)) {
            throw new RegraDeNegocioException("Horário de fim não deve ser igual ao horário de início");
        }
    }

    private void validarDias(DiaSemana diaInicio, DiaSemana diaFim) {
        if (diaInicio == diaFim) {
            throw new RegraDeNegocioException("Dias não podem ser iguais");
        }
    }

    private Optional<Usuario> procurarProfessor(Long professorId) {
        if (professorId == null) return Optional.empty();
        return Optional.of(usuarioRepository.findById(professorId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Professor não encontrado: " + professorId)));
    }

}
