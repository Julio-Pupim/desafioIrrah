package com.br.desafioirrah.controller;

import com.br.desafioirrah.domain.Destinatario;
import com.br.desafioirrah.service.DestinatarioService;
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
@RequestMapping("/api/destinatarios")
@AllArgsConstructor
public class DestinatarioController {

  private final DestinatarioService destinatarioService;

  @GetMapping
  public ResponseEntity<List<Destinatario>> findAll() {
    return ResponseEntity.ok(this.destinatarioService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Destinatario> findById(@PathVariable Long id) {
    Optional<Destinatario> destinatarioOptional = this.destinatarioService.findById(id);

    return destinatarioOptional.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Destinatario> create(@RequestBody @Valid Destinatario destinatario) {
    return ResponseEntity.ok(this.destinatarioService.save(destinatario));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Destinatario> update(@PathVariable Long id,
      @RequestBody @Valid Destinatario destinatario) {

    Destinatario destinatarioUpdated = this.destinatarioService.update(id, destinatario);

    if (Objects.nonNull(destinatarioUpdated)) {
      return ResponseEntity.ok(destinatarioUpdated);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    this.destinatarioService.delete(id);

    return ResponseEntity.noContent().build();
  }
}
