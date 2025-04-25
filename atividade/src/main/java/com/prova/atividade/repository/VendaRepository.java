package com.prova.atividade.repository;

import com.prova.atividade.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Busca vendas por ID do cliente
    List<Venda> findByClienteId(Long clienteId);
}
