package edu.technosplay.NextClass.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.technosplay.NextClass.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JwtService {
    @Value("${nextclass.security.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withSubject(usuario.getUsername())
                .withClaim("role", usuario.getRole().name())
                .withClaim("nome", usuario.getNome())
                .withExpiresAt(Instant.now().plusSeconds(86400))
                .withIssuedAt(Instant.now())
                .withIssuer("NextClass")
                .sign(algorithm);
    }

    public boolean validarToken(String token) {
        try {
            decodificar(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    private DecodedJWT decodificar(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.require(algorithm).build().verify(token);
    }

    public String extrairEmail(String token) {
        return decodificar(token).getSubject();
    }

    public String extrairRole(String token) {
        return decodificar(token).getClaim("role").asString();
    }
}
