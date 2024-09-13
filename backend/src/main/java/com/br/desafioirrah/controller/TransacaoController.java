package com.br.desafioirrah.controller;

import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.record.TransacaoRecord;
import com.br.desafioirrah.service.TransacaoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
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
@RequestMapping("/api/transacoes")
@AllArgsConstructor
public class TransacaoController {

  private final TransacaoService transacaoService;

  @PostMapping
  public ResponseEntity<Transacao> save(@RequestBody @Valid TransacaoRecord transacao) {
    return ResponseEntity.ok(transacaoService.save(transacao));
  }

  @GetMapping
  public ResponseEntity<List<Transacao>> findAll() {
    return ResponseEntity.ok(transacaoService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Transacao> findById(@PathVariable Long id) {
    Optional<Transacao> optionalTransacao = this.transacaoService.findById(id);
    return optionalTransacao.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Transacao> update(@PathVariable Long id,
      @RequestBody TransacaoRecord transacao) {
    Transacao transcaoUpdate = this.transacaoService.update(id, transacao);
    if (Objects.nonNull(transcaoUpdate)) {
      return ResponseEntity.ok(transcaoUpdate);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.transacaoService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
