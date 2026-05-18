package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.mapper.CursoMapper;
import edu.technosplay.NextClass.repository.CursoRepository;
import edu.technosplay.NextClass.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {
    private final CursoRepository cursoRepository;

    @Override
    public List<CursoResponse> listar() {
        return cursoRepository.findAll().stream()
                .map(CursoMapper::toResponse)
                .toList();
    }
}
