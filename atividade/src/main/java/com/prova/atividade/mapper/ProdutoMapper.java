package com.prova.atividade.mapper;

import com.prova.atividade.dto.ProdutoRequestDTO;
import com.prova.atividade.dto.ProdutoResponseDTO;
import com.prova.atividade.model.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
    public Produto toEntity(ProdutoRequestDTO request) {
        Produto produto = new Produto();
        produto.setNome(request.getNome());
        produto.setDescricao(request.getDescricao());
        produto.setPreco(request.getPreco());
        return produto;
    }

    // Alterado de toResponse() para toResponseDTO() para padronizar
    public ProdutoResponseDTO toResponseDTO(Produto produto) {
        ProdutoResponseDTO response = new ProdutoResponseDTO();
        response.setId(produto.getId());
        response.setNome(produto.getNome());
        response.setDescricao(produto.getDescricao());
        response.setPreco(produto.getPreco());
        return response;
    }
}
