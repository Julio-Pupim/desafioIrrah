package com.br.desafioirrah.infra.security;

import com.br.desafioirrah.domain.Administrador;
import com.br.desafioirrah.repository.AdministradorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {


  private final TokenService tokenService;

  private final AdministradorRepository administradorRepository;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var token = this.recuperarToken(request);
    var login = tokenService.validarToken(token);

    if (login != null) {
      Administrador admin = administradorRepository.findByEmail(login)
          .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado"));

      var autorizacoes = Collections.singletonList(
          new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));

      var autenticacao = new UsernamePasswordAuthenticationToken(admin, null, autorizacoes);

      SecurityContextHolder.getContext().setAuthentication(autenticacao);

    }
    filterChain.doFilter(request, response);
  }

  private String recuperarToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      return authHeader.replace("Bearer ", "");
    }
    return null;
  }
}
