package com.br.desafioirrah.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.br.desafioirrah.domain.Cliente;
import com.br.desafioirrah.domain.Transacao;
import com.br.desafioirrah.domain.enums.PlanoPagamento;
import com.br.desafioirrah.domain.enums.TipoTransacao;
import com.br.desafioirrah.domain.record.TransacaoRecord;
import com.br.desafioirrah.repository.TransacaoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {

  @InjectMocks
  private TransacaoService transacaoService;

  @Mock
  private TransacaoRepository transacaoRepository;

  @Mock
  private ClienteService clienteService;

  private Cliente cliente;

  @BeforeEach
  public void setUp() {

    cliente = new Cliente(1L, "Julio", "14470749044", "julio.pupim1@gmail.com",
        "68927584724", "80267250000121", "Julio jobs", PlanoPagamento.PRE_PAGO, BigDecimal.ZERO,
        null, new ArrayList<>());
  }

  @Test
  public void saveTransacaoDebito() {

    TransacaoRecord transacaoRecord = new TransacaoRecord(BigDecimal.valueOf(100.00),
        LocalDate.now(),
        TipoTransacao.DEBITO, "teste", 1L);

    when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

    this.transacaoService.save(transacaoRecord);

    verify(transacaoRepository, times(1)).save(any());

    assertThat(cliente.getTransacoes()).hasSize(1);
    assertThat(cliente.getTransacoes().get(0))
        .isEqualTo(Transacao.toEntity(transacaoRecord, cliente));
    assertThat(cliente.getSaldo()).isEqualTo(BigDecimal.valueOf(100.00));
  }

  @Test
  public void saveTransacaoCredito() {

    TransacaoRecord transacaoRecord = new TransacaoRecord(BigDecimal.valueOf(100.00),
        LocalDate.now(),
        TipoTransacao.CREDITO, "teste", 1L);

    when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

    assertThatThrownBy(() -> this.transacaoService.save(transacaoRecord))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Cliente não possui saldo ou limite para realizar operação!");

    assertThat(cliente.getTransacoes()).isEmpty();
    verifyNoInteractions(transacaoRepository);

  }

  @Test
  public void saveTransacaoDebitoCredito() {

    TransacaoRecord transacaoRecordDebito = new TransacaoRecord(BigDecimal.valueOf(100.00),
        LocalDate.now(),
        TipoTransacao.DEBITO, "teste", 1L);

    TransacaoRecord transacaoRecordCredito = new TransacaoRecord(BigDecimal.valueOf(100.00),
        LocalDate.now(),
        TipoTransacao.CREDITO, "teste", 1L);

    when(clienteService.findById(1L)).thenReturn(Optional.of(cliente));

    this.transacaoService.save(transacaoRecordDebito);

    assertThat(cliente.getTransacoes()).hasSize(1);
    assertThat(cliente.getSaldo()).isEqualTo(BigDecimal.valueOf(100.00));

    this.transacaoService.save(transacaoRecordCredito);

    assertThat(cliente.getTransacoes()).hasSize(2);
    assertThat(cliente.getSaldo()).isEqualByComparingTo(BigDecimal.ZERO);

    verify(transacaoRepository, times(2)).save(any());

  }

}