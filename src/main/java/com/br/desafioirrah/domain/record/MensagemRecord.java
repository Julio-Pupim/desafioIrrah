package com.br.desafioirrah.domain.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemRecord(
    @NotBlank String numeroTelefone,
    @NotNull Boolean isWhatsApp,
    @NotBlank String mensagem,
    @NotNull Long cliente
) {

}
