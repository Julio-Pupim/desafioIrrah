package com.br.desafioirrah.repository;

import com.br.desafioirrah.domain.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long>,
    JpaSpecificationExecutor<Mensagem> {

}
