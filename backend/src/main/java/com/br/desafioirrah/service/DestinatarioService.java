package com.br.desafioirrah.service;

import com.br.desafioirrah.domain.Destinatario;
import com.br.desafioirrah.repository.DestinatarioRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DestinatarioService {

  private final DestinatarioRepository destinatarioRepository;

  public Destinatario save(Destinatario destinatario) {
    return this.destinatarioRepository.save(destinatario);
  }

  public List<Destinatario> findAll() {
    return this.destinatarioRepository.findAll();
  }

  public Optional<Destinatario> findById(Long id) {
    return this.destinatarioRepository.findById(id);
  }

  public Destinatario update(Long id, Destinatario destinatario) {

    Destinatario dest = destinatarioRepository.findById(id).orElse(null);

    if (dest != null) {
      BeanUtils.copyProperties(destinatario, dest, "id");
      return this.destinatarioRepository.save(dest);
    }
    return null;
  }

  public void delete(Long id) {
    this.destinatarioRepository.deleteById(id);
  }
}
