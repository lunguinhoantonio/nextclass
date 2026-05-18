package edu.technosplay.NextClass.controller;

import edu.technosplay.NextClass.dto.response.CursoResponse;
import edu.technosplay.NextClass.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nextclass/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<CursoResponse>> listar() {
        return ResponseEntity.ok(cursoService.listar());
    }

}
