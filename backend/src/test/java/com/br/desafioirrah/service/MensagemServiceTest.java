package com.br.desafioirrah.service;

import static org.mockito.Mockito.verify;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.Destinatario;
import com.br.desafioirrah.domain.Mensagem;
import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.enums.PlanoPagamento;
import com.br.desafioirrah.domain.enums.TipoTransacao;
import com.br.desafioirrah.repository.MensagemRepository;
import com.br.desafioirrah.repository.TransacaoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MensagemServiceTest {

  @InjectMocks
  private MensagemService mensagemService;

  @Mock
  private MensagemRepository mensagemRepository;

  @Mock
  private ClienteService clienteService;

  @Mock
  private TransacaoService transacaoService;
  @Mock
  private TransacaoRepository transacaoRepository;

  private Cliente cliente;

  private Mensagem mensagem;

  @BeforeEach
  public void setUp() {
    cliente = new Cliente(1L, "Julio", "14470749044", "julio.pupim1@gmail.com",
        "68927584724", "80267250000121", "Julio jobs", PlanoPagamento.PRE_PAGO,
        BigDecimal.valueOf(100.00),
        new ArrayList<>(), new ArrayList<>());
    mensagem = new Mensagem(1L, "68927584724", true, "Olá, Cliente", cliente,
        new ArrayList<>());
  }

  @Test
  public void enviarMensagem() {

    Destinatario destinatario1 = new Destinatario(1L, "nome1", "69926686867", mensagem);
    Destinatario destinatario2 = new Destinatario(1L, "nome2", "95928157527", mensagem);
    Destinatario destinatario3 = new Destinatario(1L, "nome3", "67931712611", mensagem);

    Transacao transacao = new Transacao(null, BigDecimal.valueOf(0.75), LocalDate.now(),
        TipoTransacao.CREDITO,
        "envio de 3 mensagens", cliente);

    mensagem.getDestinatarios().add(destinatario1);
    mensagem.getDestinatarios().add(destinatario2);
    mensagem.getDestinatarios().add(destinatario3);
    cliente.getMensagens().add(mensagem);

    this.mensagemService.enviarMensagem(mensagem);

    verify(transacaoService).save(transacao);

  }

}