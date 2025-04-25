package com.prova.atividade.service;

import com.prova.atividade.dto.ProdutoRequestDTO;
import com.prova.atividade.dto.ProdutoResponseDTO;
import com.prova.atividade.mapper.ProdutoMapper;
import com.prova.atividade.model.Produto;
import com.prova.atividade.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    //Criar
    public ProdutoResponseDTO criar(ProdutoRequestDTO request) {
        Produto produto = produtoMapper.toEntity(request);
        produto.setEstoque(request.getEstoque() != null ? request.getEstoque() : 0);
        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    //Buscar por ID
    public ProdutoResponseDTO buscarPorId(Long id) {
        return produtoMapper.toResponseDTO(
                produtoRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado"))
        );
    }

    //Get All
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll().stream()
                .map(produtoMapper::toResponseDTO)
                .toList();
    }

    //Deletar
    public void deletar(Long id) {
        produtoRepository.deleteById(id);
    }

    //Atualizar
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        produto.setEstoque(request.getEstoque() != null ? request.getEstoque() : produto.getEstoque());

        return produtoMapper.toResponseDTO(produtoRepository.save(produto));
    }

    // Busca produtos com estoque abaixo de um valor
    public List<Produto> buscarComEstoqueAbaixoDe(int quantidade) {
        return produtoRepository.findByEstoqueLessThan(quantidade);
    }

    // Busca produtos por nome (contendo palavra-chave)
    public List<Produto> buscarPorNome(String palavraChave) {
        return produtoRepository.findByNomeContainingIgnoreCase(palavraChave);
    }

}
