package com.prova.atividade.repository;

import com.prova.atividade.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Busca produtos com estoque MENOR que o valor informado
    List<Produto> findByEstoqueLessThan(int quantidade);

    // Opcional: Busca produtos com estoque MAIOR que o valor informado
    List<Produto> findByEstoqueGreaterThan(int quantidade);

    // Busca produtos cujo nome CONTÉM a palavra-chave (ignorando maiúsculas/minúsculas)
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    // Opcional: Busca produtos que COMEÇAM com a palavra-chave
    List<Produto> findByNomeStartingWithIgnoreCase(String nome);


}
