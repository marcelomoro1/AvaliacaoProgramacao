package com.prova.atividade.mapper;

import com.prova.atividade.dto.ItemVendaRequestDTO;
import com.prova.atividade.dto.ItemVendaResponseDTO;
import com.prova.atividade.mapper.ProdutoMapper;
import com.prova.atividade.model.ItemVenda;
import com.prova.atividade.model.Produto;
import com.prova.atividade.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ItemVendaMapper {
    @Autowired
    private ProdutoRepository produtoRepository; // Adicionado

    @Autowired
    private ProdutoMapper produtoMapper;

    public ItemVenda toEntity(ItemVendaRequestDTO dto) {
        ItemVenda item = new ItemVenda();
        item.setQuantidade(dto.getQuantidade());

        // Busca o produto completo e pega o preço cadastrado
        Produto produto = produtoRepository.findById(dto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        item.setValorUnitario(produto.getPreco()); // Valor automático
        item.setProduto(produto);

        return item;
    }

    // Mantenha o toResponseDTO igual
    public ItemVendaResponseDTO toResponseDTO(ItemVenda item) {
        ItemVendaResponseDTO dto = new ItemVendaResponseDTO();
        dto.setId(item.getId());
        dto.setQuantidade(item.getQuantidade());
        dto.setValorUnitario(item.getValorUnitario());
        dto.setProduto(produtoMapper.toResponseDTO(item.getProduto()));
        return dto;
    }
}