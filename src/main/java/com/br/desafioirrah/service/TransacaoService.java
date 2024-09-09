package com.br.desafioirrah.service;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.enums.TipoTransacao;
import com.br.desafioirrah.domain.record.TransacaoRecord;
import com.br.desafioirrah.repository.TransacaoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransacaoService {

  private final TransacaoRepository transacaoRepository;
  private final ClienteService clienteService;

  public Transacao save(TransacaoRecord transacao) {

    Cliente cliente = clienteService.findById(transacao.cliente())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

    if (TipoTransacao.CREDITO.equals(transacao.tipo())) {
      BigDecimal novoSaldo = cliente.getSaldo().subtract(transacao.valor());

      if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException(
            "Cliente não possui saldo ou limite para realizar operação!");
      }
      cliente.setSaldo(novoSaldo);
    } else {
      BigDecimal novoSaldo = cliente.getSaldo().add(transacao.valor());
      cliente.setSaldo(novoSaldo);
    }
    Transacao transacaoSave = Transacao.toEntity(transacao, cliente);
    cliente.getTransacoes().add(transacaoSave);

    return this.transacaoRepository.save(transacaoSave);
  }

  public Transacao save(Transacao transacao) {

    if (TipoTransacao.CREDITO.equals(transacao.getTipoTransacao())) {
      BigDecimal novoSaldo = transacao.getCliente().getSaldo().subtract(transacao.getValor());

      if (novoSaldo.compareTo(BigDecimal.ZERO) < 0) {
        throw new IllegalArgumentException(
            "Cliente não possui saldo ou limite para realizar operação!");
      }
      transacao.getCliente().setSaldo(novoSaldo);
    } else {
      BigDecimal novoSaldo = transacao.getCliente().getSaldo().add(transacao.getValor());
      transacao.getCliente().setSaldo(novoSaldo);
    }
    transacao.getCliente().getTransacoes().add(transacao);

    return this.transacaoRepository.save(transacao);
  }

  public List<Transacao> findAll() {
    return transacaoRepository.findAll();
  }

  public Optional<Transacao> findById(Long id) {
    return this.transacaoRepository.findById(id);
  }

  public Transacao update(Long id, TransacaoRecord transacao) {

    Transacao transcaoExistente = this.transacaoRepository.findById(id).orElse(null);

    if (transcaoExistente != null) {
      Cliente cliente = clienteService.findById(transacao.cliente())
          .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

      BeanUtils.copyProperties(transacao, transcaoExistente, "cliente");

      return this.transacaoRepository.save(transcaoExistente);
    }
    return null;
  }

  public void delete(Long id) {
    transacaoRepository.deleteById(id);
  }

}
