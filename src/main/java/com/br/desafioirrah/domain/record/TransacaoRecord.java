package com.br.desafioirrah.domain.record;

import com.br.desafioirrah.domain.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.math.BigDecimal;
import java.time.LocalDate;

public record TransacaoRecord(
    @NotNull BigDecimal valor,
    @NotNull LocalDate data,
    @NotNull TipoTransacao tipo,
    @Null String descricao,
    @NotNull Long cliente
) {

}
