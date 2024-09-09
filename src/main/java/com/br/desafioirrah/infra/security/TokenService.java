package com.br.desafioirrah.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.br.desafioirrah.domain.Administrador;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Value("${api.security.token.secret}")
  private String secret;

  public String gerarToken(Administrador admin) {
    try {

      Algorithm algorithm = Algorithm.HMAC256(secret);

      //WithIssuer (criador do token, a aplicação) Subject(quem é endereçado a autenticacao)
      String token = JWT.create().withIssuer("desafio-irrah")
          .withSubject(admin.getEmail())
          .withExpiresAt(this.gerarTempoValido())
          .sign(algorithm);

      return token;

    } catch (JWTCreationException exception) {
      throw new RuntimeException("Erro ao criar token de autenticação");
    }
  }

  public String validarToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      return JWT.require(algorithm)
          .withIssuer("desafio-irrah")
          .build()
          .verify(token)
          .getSubject();
    } catch (JWTVerificationException exception) {

      return null;
    }
  }

  private Instant gerarTempoValido() {

    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
  }
}
