package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.model.enums.Role;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse buscarPorId(Long id);
    List<UsuarioResponse> listar(Role role, Boolean ativo);
    UsuarioResponse ativar(Long id);
    UsuarioResponse desativar(Long id);
}
