package com.br.desafioirrah.domain.record;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestRecord(

    @Email String email,
    @NotBlank String senha) {

}
