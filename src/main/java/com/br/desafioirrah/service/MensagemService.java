package com.br.desafioirrah.service;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.Mensagem;
import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.enums.TipoTransacao;
import com.br.desafioirrah.domain.record.MensagemRecord;
import com.br.desafioirrah.repository.MensagemRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MensagemService {

  private final MensagemRepository mensagemRepository;
  private final ClienteService clienteService;
  private final TransacaoService transacaoService;

  private static BigDecimal VALOR_ENVIO_MENSAGEM = BigDecimal.valueOf(0.25);

  public Mensagem save(MensagemRecord mensagem) {

    Cliente cliente = this.clienteService.findById(mensagem.cliente())
        .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

    Mensagem mensagemSave = Mensagem.toEntity(mensagem, cliente);
    cliente.getMensagens().add(mensagemSave);

    return this.mensagemRepository.save(mensagemSave);
  }

  public List<Mensagem> findAll() {
    return this.mensagemRepository.findAll();
  }

  public Optional<Mensagem> findById(Long id) {
    return this.mensagemRepository.findById(id);
  }

  public Mensagem update(Long id, MensagemRecord mensagem) {

    Mensagem mensagemExistente = this.findById(id).orElse(null);

    if (mensagemExistente != null) {
      Cliente cliente = this.clienteService.findById(mensagem.cliente())
          .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado"));

      BeanUtils.copyProperties(mensagem, mensagemExistente, "cliente");

      return this.mensagemRepository.save(mensagemExistente);
    }
    return null;
  }

  public void delete(Long id) {
    this.mensagemRepository.deleteById(id);
  }

  public void enviarMensagem(Mensagem mensagem) {

    int qtdeMensagens = mensagem.getDestinatarios().size();
    BigDecimal valorTotalMensagens = VALOR_ENVIO_MENSAGEM.multiply(
        BigDecimal.valueOf(qtdeMensagens));

    Transacao transacao = new Transacao();
    transacao.setTipoTransacao(TipoTransacao.CREDITO);
    transacao.setValor(valorTotalMensagens);
    transacao.setCliente(mensagem.getCliente());
    transacao.setData(LocalDate.now());
    transacao.setDescricao(String.format("envio de %s mensagens", qtdeMensagens));

    transacaoService.save(transacao);

  }
}

