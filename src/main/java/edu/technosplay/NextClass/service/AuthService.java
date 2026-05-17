package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.UsuarioRequest;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;

public interface AuthService {
    UsuarioResponse registrar(UsuarioRequest request);
}
