package com.br.desafioirrah.domain.record;

import com.br.desafioirrah.domain.enums.PlanoPagamento;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRecord(
    @NotBlank String nome,
    @NotNull @CPF String cpf,
    @NotNull @Email String email,
    @NotBlank String telefone,
    @CNPJ String cnpj,
    @NotBlank String nomeEmpresa,
    PlanoPagamento planoPagamento,
    BigDecimal saldo) {

}
