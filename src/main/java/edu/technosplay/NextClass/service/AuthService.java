package edu.technosplay.NextClass.service;

import edu.technosplay.NextClass.dto.request.LoginRequest;
import edu.technosplay.NextClass.dto.request.UsuarioRequest;
import edu.technosplay.NextClass.dto.response.LoginResponse;
import edu.technosplay.NextClass.dto.response.UsuarioResponse;

public interface AuthService {
    UsuarioResponse registrar(UsuarioRequest request);
    LoginResponse login(LoginRequest request);
}
