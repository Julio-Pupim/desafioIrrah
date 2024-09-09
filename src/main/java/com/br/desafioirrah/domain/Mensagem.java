package com.br.desafioirrah.domain;

import com.br.desafioirrah.domain.record.MensagemRecord;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Table(name = "mensagem")
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "numero_telefone")
  @NotBlank
  private String numeroTelefone;

  @Column(name = "whats_app")
  @NotNull
  private Boolean isWhatsApp;

  @NotBlank
  private String mensagem;

  @ManyToOne
  @JoinColumn
  @ToString.Exclude
  private Cliente cliente;

  @OneToMany(mappedBy = "mensagem", orphanRemoval = true)
  @ToString.Exclude
  private List<Destinatario> destinatarios;

  public static Mensagem toEntity(MensagemRecord dto, Cliente cliente) {
    Mensagem mensagem = new Mensagem();
    mensagem.setId(null);
    mensagem.setNumeroTelefone(dto.numeroTelefone());
    mensagem.setIsWhatsApp(dto.isWhatsApp());
    mensagem.setMensagem(dto.mensagem());
    mensagem.setCliente(cliente);
    return mensagem;
  }
}
