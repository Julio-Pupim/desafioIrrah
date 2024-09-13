package com.br.desafioirrah.repository;

import com.br.desafioirrah.domain.Destinatario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinatarioRepository extends JpaRepository<Destinatario, Long>,
    JpaSpecificationExecutor<Destinatario> {

}
