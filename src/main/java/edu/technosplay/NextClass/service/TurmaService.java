package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.TurmaRequest;
import edu.technosplay.NextClass.dto.response.TurmaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TurmaService {
    TurmaResponse criar(TurmaRequest request);
    TurmaResponse buscarPorId(Long id);
    List<TurmaResponse> listar(Long cursoId, Boolean ativo);
    TurmaResponse desativar(Long id);
    TurmaResponse ativar(Long id);
}
