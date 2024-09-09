package com.br.desafioirrah.domain;


import com.br.desafioirrah.domain.enums.PlanoPagamento;
import com.br.desafioirrah.domain.record.ClienteRecord;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Data
@Entity
@Table(name = "cliente")
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String nome;

  @NotBlank
  @CPF
  private String cpf;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String telefone;

  @CNPJ
  private String cnpj;

  @NotBlank
  @Column(name = "nome_empresa")
  private String nomeEmpresa;

  @Enumerated(EnumType.STRING)
  @Column(name = "plano_pagamento")
  private PlanoPagamento planoPagamento;


  private BigDecimal saldo = BigDecimal.ZERO;

  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
  private List<Mensagem> mensagens;

  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cliente")
  private List<Transacao> transacoes;

  public static Cliente toEntity(ClienteRecord dto) {
    Cliente cliente = new Cliente();
    cliente.setId(null);
    cliente.setNome(dto.nome());
    cliente.setCpf(dto.cpf());
    cliente.setEmail(dto.email());
    cliente.setTelefone(dto.telefone());
    cliente.setCnpj(dto.cnpj());
    cliente.setNomeEmpresa(dto.nomeEmpresa());
    cliente.setPlanoPagamento(dto.planoPagamento());
    cliente.setSaldo(dto.saldo());
    return cliente;
  }
}
