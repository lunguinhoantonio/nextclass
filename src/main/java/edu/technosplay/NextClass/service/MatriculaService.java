package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.MatriculaRequest;
import edu.technosplay.NextClass.dto.response.MatriculaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MatriculaService {
    MatriculaResponse matricular(Long alunoId, MatriculaRequest request);
    MatriculaResponse cancelar(Long matriculaId, Long alunoId);
    List<MatriculaResponse> listarPorAluno(Long alunoId);
    MatriculaResponse buscarPorId(Long id);
}
