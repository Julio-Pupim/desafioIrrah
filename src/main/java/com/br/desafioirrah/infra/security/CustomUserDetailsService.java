package com.br.desafioirrah.infra.security;

import com.br.desafioirrah.domain.Administrador;
import com.br.desafioirrah.repository.AdministradorRepository;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

  private final AdministradorRepository administradorRepository;


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Administrador administrador = administradorRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Administrador não encontrado"));
    return new User(administrador.getEmail(), administrador.getSenha(), new ArrayList<>());
  }
}
