package com.br.desafioirrah.service;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.record.ClienteRecord;
import com.br.desafioirrah.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteService {

  private final ClienteRepository clienteRepository;


  public Cliente save(Cliente cliente) {
    return this.clienteRepository.save(cliente);
  }

  public List<Cliente> findAll() {
    return this.clienteRepository.findAll();
  }

  public Optional<Cliente> findById(Long id) {

    return this.clienteRepository.findById(id);
  }

  public Cliente update(Long id, ClienteRecord cliente) {

    Cliente clienteExistente = this.findById(id).orElse(null);

    if (clienteExistente != null) {
      BeanUtils.copyProperties(cliente, clienteExistente);
      return this.save(clienteExistente);
    }
    return null;
  }

  public void delete(Long id) {
    this.clienteRepository.deleteById(id);
  }


}
