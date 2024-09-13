package com.br.desafioirrah.controller;

import com.br.desafioirrah.domain.Mensagem;
import com.br.desafioirrah.domain.record.MensagemRecord;
import com.br.desafioirrah.service.MensagemService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/mensagens")
@AllArgsConstructor
public class MensagemController {

  private final MensagemService mensagemService;

  @PostMapping
  public ResponseEntity<Mensagem> save(@RequestBody @Valid MensagemRecord mensagem) {
    return ResponseEntity.ok(mensagemService.save(mensagem));
  }

  @GetMapping
  public ResponseEntity<List<Mensagem>> findAll() {
    return ResponseEntity.ok(mensagemService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Mensagem> findById(@PathVariable Long id) {
    Optional<Mensagem> mensagem = this.mensagemService.findById(id);
    return mensagem.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Mensagem> update(@PathVariable Long id,
      @RequestBody @Valid MensagemRecord mensagem) {
    Mensagem mensagemUpdated = this.mensagemService.update(id, mensagem);
    if (mensagemUpdated != null) {
      return ResponseEntity.ok(mensagemUpdated);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.mensagemService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
