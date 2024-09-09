package com.br.desafioirrah.controller;

import com.br.desafioirrah.domain.Administrador;
import com.br.desafioirrah.domain.record.AdministradorRecord;
import com.br.desafioirrah.domain.record.LoginRequestRecord;
import com.br.desafioirrah.domain.record.ResponseRecord;
import com.br.desafioirrah.infra.security.TokenService;
import com.br.desafioirrah.repository.AdministradorRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AdministradorRepository administradorRepository;

  private final PasswordEncoder passwordEncoder;

  private final TokenService tokenService;

  @PostMapping("/login")
  public ResponseEntity<ResponseRecord> login(@RequestBody LoginRequestRecord administrador) {

    Administrador admin = administradorRepository.findByEmail(administrador.email())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));
    if (passwordEncoder.matches(administrador.senha(), administrador.email())) {
      String token = tokenService.gerarToken(admin);
      return ResponseEntity.ok(new ResponseRecord(administrador.email(), token));
    }
    return ResponseEntity.badRequest().build();
  }

  @PostMapping("/registrar")
  public ResponseEntity<ResponseRecord> registrar(@RequestBody AdministradorRecord body) {
    Optional<Administrador> admin = administradorRepository.findByEmail(body.email());

    if (admin.isPresent()) {
      Administrador newAdmin = new Administrador();
      newAdmin.setEmail(body.email());
      newAdmin.setSenha(body.senha());
      this.administradorRepository.save(newAdmin);
      String token = tokenService.gerarToken(newAdmin);
      return ResponseEntity.ok(new ResponseRecord(newAdmin.getEmail(), token));
    }
    return ResponseEntity.badRequest().build();
  }

}
