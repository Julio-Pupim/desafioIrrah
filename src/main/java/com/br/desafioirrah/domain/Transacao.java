package com.br.desafioirrah.domain;

import com.br.desafioirrah.domain.enums.TipoTransacao;
import com.br.desafioirrah.domain.record.TransacaoRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "transacao")
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private BigDecimal valor;

  private LocalDate data;

  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_transacao")
  @NotNull
  private TipoTransacao tipoTransacao;

  private String descricao;

  @ManyToOne
  @NotNull
  @ToString.Exclude
  private Cliente cliente;

  public static Transacao toEntity(TransacaoRecord dto, Cliente cliente) {
    Transacao transacao = new Transacao();
    transacao.setTipoTransacao(dto.tipo());
    transacao.setDescricao(dto.descricao());
    transacao.setCliente(cliente);
    transacao.setValor(dto.valor());
    transacao.setData(dto.data());
    return transacao;
  }


}
