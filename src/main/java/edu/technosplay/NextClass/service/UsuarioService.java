package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.AlunoRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.model.enums.StatusAluno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlunoService {
    UsuarioResponse criar(AlunoRequest request);
    UsuarioResponse buscarPorId(Long id);
    UsuarioResponse atualizar(Long id, AlunoRequest request);
    void inativar(Long id);
    Page<UsuarioResponse> listar(Pageable pageable);
    Page<UsuarioResponse> listarPorStatus(StatusAluno status, Pageable pageable);
    Page<UsuarioResponse> buscarPorTermo(String termo, Pageable pageable);

}
