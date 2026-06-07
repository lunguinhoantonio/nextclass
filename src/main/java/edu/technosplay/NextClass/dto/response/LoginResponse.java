package edu.technosplay.NextClass.dto.response;

import edu.technosplay.NextClass.model.enums.Role;

public record LoginResponse(
        String token,
        String tipo,
        String nome,
        String email,
        Role role
) {
    public LoginResponse(String token, String nome, String email, Role role) {
        this(token, "Bearer", nome, email, role);
    }
}
