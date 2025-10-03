package br.com.vetelemed.api.auth.service;

import br.com.vetelemed.api.auth.model.UserModel;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    private final Algorithm algorithm = Algorithm.HMAC256("12345678");

    public String generateToken(UserModel userModel) {
        try{
            return JWT.create()
                    .withIssuer("vetelemed-api")
                    .withSubject(userModel.getUsername())
                    .withClaim("role", "ROLE_USER")
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 7200000))
                    .sign(algorithm);
        }
        catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String getSubject(String token) {
        try{
            return JWT.require(algorithm)
                    .withIssuer("vetelemed-api")
                    .withClaim("role", "ROLE_USER")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTCreationException e){
            throw new RuntimeException("Token JWT inválido ou expirado", e);
        }

    }

    public boolean verifyToken(String token, String username) {
        try{
            JWT.require(algorithm)
                    .withSubject(username)
                    .withClaim("role", "ROLE_USER")
                    .withIssuer("vetelemed-api")
                    .build()
                    .verify(token);
            return true;
        }
        catch (JWTCreationException e){
            return false;
        }
    }
}
