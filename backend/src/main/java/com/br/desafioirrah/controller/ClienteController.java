package com.br.desafioirrah.controller;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.Mensagem;
import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.record.ClienteRecord;
import com.br.desafioirrah.service.ClienteService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
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
@RequestMapping("/api/clientes")
@AllArgsConstructor
public class ClienteController {

  private final ClienteService clienteService;

  @GetMapping
  public ResponseEntity<List<Cliente>> findAll() {
    return ResponseEntity.ok(clienteService.findAll());
  }

  @PostMapping
  public ResponseEntity<Cliente> save(@RequestBody @Valid ClienteRecord cliente) {

    return ResponseEntity.ok(clienteService.save(Cliente.toEntity(cliente)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Cliente> findById(@PathVariable Long id) {
    Cliente cliente = clienteService.findById(id).orElse(null);
    if (cliente != null) {
      return ResponseEntity.ok(cliente);
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}/mensagens")
  public ResponseEntity<List<Mensagem>> getMensagens(@PathVariable Long id) {

    Cliente cliente = clienteService.findById(id).orElse(null);
    if (Objects.nonNull(cliente)) {
      return ResponseEntity.ok(cliente.getMensagens());
    }
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/{id}/transacoes")
  public ResponseEntity<List<Transacao>> getTransacoes(@PathVariable Long id) {
    Cliente cliente = clienteService.findById(id).orElse(null);
    if (Objects.nonNull(cliente)) {
      return ResponseEntity.ok(cliente.getTransacoes());
    }
    return ResponseEntity.notFound().build();
  }


  @PutMapping("/{id}")
  public ResponseEntity<Cliente> update(@PathVariable Long id,
      @RequestBody @Valid ClienteRecord cliente) {
    Cliente clienteUpdated = clienteService.update(id, cliente);
    if (clienteUpdated != null) {
      return ResponseEntity.ok(clienteUpdated);
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    clienteService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
