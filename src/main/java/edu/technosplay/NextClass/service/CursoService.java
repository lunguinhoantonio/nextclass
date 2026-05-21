package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.CursoPatchRequest;
import edu.technosplay.NextClass.dto.request.CursoRequest;
import edu.technosplay.NextClass.dto.response.CursoResponse;

import java.util.List;

public interface CursoService {
    CursoResponse criar(CursoRequest request);
    CursoResponse atualizar(Long id, CursoRequest request);
    CursoResponse atualizarPatch(Long id, CursoPatchRequest request);
    List<CursoResponse> listar(Long professorId, Boolean ativo);
    CursoResponse listarPorId(Long id);
    CursoResponse desativar(Long id);
    CursoResponse ativar(Long id);

}
