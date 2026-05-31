package edu.technosplay.NextClass.controller.ui;

import edu.technosplay.NextClass.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final CursoService cursoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("paginaAtual", "inicio");
        model.addAttribute("cursos", cursoService.listar(null, true));
        return "index";
    }

    @GetMapping("/cursos")
    public String paginaCursos(Model model) {
        model.addAttribute("cursos", cursoService.listar(null, true));
        return "cursos";
    }

    @GetMapping("/atendimento")
    public String paginaAtendimento() {
        return "atendimento";
    }
}
