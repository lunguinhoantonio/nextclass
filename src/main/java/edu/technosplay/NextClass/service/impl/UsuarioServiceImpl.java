package edu.technosplay.NextClass.service.impl;

import edu.technosplay.NextClass.dto.response.UsuarioResponse;
import edu.technosplay.NextClass.exception.*;
import edu.technosplay.NextClass.mapper.UsuarioMapper;
import edu.technosplay.NextClass.model.Usuario;
import edu.technosplay.NextClass.model.enums.Role;
import edu.technosplay.NextClass.repository.UsuarioRepository;
import edu.technosplay.NextClass.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .map(UsuarioMapper::toResponse)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar(Role role, Boolean ativo) {
        List<Usuario> usuarios;

        if (role != null && ativo != null) {
            usuarios = usuarioRepository.findAllByRoleAndAtivo(role, ativo);
        } else if (role != null) {
            usuarios = usuarioRepository.findAllByRole(role);
        } else if (ativo != null) {
            usuarios = usuarioRepository.findAllByAtivo(ativo);
        } else {
            usuarios = usuarioRepository.findAll();
        }

        return usuarios.stream()
                .map(UsuarioMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public UsuarioResponse ativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
        usuario.setAtivo(true);
        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    @Transactional
    public UsuarioResponse desativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado: " + id));
        usuario.setAtivo(false);
        return UsuarioMapper.toResponse(usuarioRepository.save(usuario));
    }
}
