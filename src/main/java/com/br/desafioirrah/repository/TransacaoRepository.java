package com.br.desafioirrah.repository;

import com.br.desafioirrah.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>,
    JpaSpecificationExecutor<Transacao> {

}
