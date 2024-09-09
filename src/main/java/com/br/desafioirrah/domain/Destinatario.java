package com.br.desafioirrah.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "destinatario")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Destinatario {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Null
  private String nome;

  @NotNull
  private String telefone;

  @ManyToOne
  @JoinColumn
  @NotNull
  @ToString.Exclude
  private Mensagem mensagem;

}
