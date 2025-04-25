package com.prova.atividade.service;

import com.prova.atividade.dto.ItemVendaRequestDTO;
import com.prova.atividade.dto.ItemVendaResponseDTO;
import com.prova.atividade.mapper.ItemVendaMapper;
import com.prova.atividade.model.*;
import com.prova.atividade.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemVendaService {
    private final ItemVendaRepository itemVendaRepository;
    private final ItemVendaMapper itemVendaMapper;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;

    // Criar item vinculado a uma venda
    public ItemVendaResponseDTO criar(ItemVendaRequestDTO request, Long vendaId) {
        // Busca venda e produto
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica estoque
        if (produto.getEstoque() < request.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente. Disponível: " + produto.getEstoque());
        }

        // Cria o item
        ItemVenda item = new ItemVenda();
        item.setQuantidade(request.getQuantidade());
        item.setProduto(produto);
        item.setVenda(venda);

        // Atualiza estoque
        produto.setEstoque(produto.getEstoque() - request.getQuantidade());
        produtoRepository.save(produto);

        return itemVendaMapper.toResponseDTO(itemVendaRepository.save(item));
    }

    // Buscar por ID
    public ItemVendaResponseDTO buscarPorId(Long id) {
        return itemVendaMapper.toResponseDTO(
                itemVendaRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Item não encontrado"))
        );
    }

    // Deletar item (com devolução de estoque)
    public void deletar(Long id) {
        ItemVenda item = itemVendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        Produto produto = item.getProduto();
        produto.setEstoque(produto.getEstoque() + item.getQuantidade());

        produtoRepository.save(produto);
        itemVendaRepository.delete(item);
    }

    // Listar itens por venda
    public List<ItemVendaResponseDTO> listarPorVenda(Long vendaId) {
        List<ItemVenda> itens = itemVendaRepository.findByVendaId(vendaId);

        return itens.stream()
                .map(itemVendaMapper::toResponseDTO)
                .collect(Collectors.toList()); // Use collect() em vez de toList() para maior compatibilidade
    }
}