package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.AlunoRequest;
import edu.technosplay.NextClass.dto.response.AlunoResponse;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlunoService {
    AlunoResponse criar(AlunoRequest request);
    AlunoResponse buscarPorId(Long id);
    AlunoResponse atualizar(Long id, AlunoRequest request);
    void inativar(Long id);
    Page<AlunoResponse> listar(Pageable pageable);
    Page<AlunoResponse> listarPorStatus(StatusAluno status, Pageable pageable);
    Page<AlunoResponse> buscarPorTermo(String termo, Pageable pageable);

}
