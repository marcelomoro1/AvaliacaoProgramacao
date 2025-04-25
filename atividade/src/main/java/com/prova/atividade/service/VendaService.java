package com.prova.atividade.service;

import com.prova.atividade.dto.ItemVendaRequestDTO;
import com.prova.atividade.dto.VendaRequestDTO;
import com.prova.atividade.dto.VendaResponseDTO;
import com.prova.atividade.mapper.ItemVendaMapper;
import com.prova.atividade.mapper.VendaMapper;
import com.prova.atividade.model.*;
import com.prova.atividade.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {
    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;
    private final ItemVendaMapper itemVendaMapper;

    public VendaResponseDTO criarVenda(VendaRequestDTO request) {
        // Validações iniciais
        if (request.getClienteId() == null) {
            throw new RuntimeException("ID do cliente é obrigatório");
        }

        if (request.getItens() == null || request.getItens().isEmpty()) {
            throw new RuntimeException("A venda deve conter pelo menos um item");
        }

        // Cria a venda básica
        Venda venda = new Venda();
        venda.setData(LocalDateTime.now());
        venda.setDescontoPercentual(
                request.getDescontoPercentual() != null ?
                        request.getDescontoPercentual() :
                        BigDecimal.ZERO
        );

        // Busca e vincula o cliente
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        venda.setCliente(cliente);

        // Processa os itens
        List<ItemVenda> itensVenda = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (ItemVendaRequestDTO itemRequest : request.getItens()) {
            // Validação básica
            if (itemRequest.getProdutoId() == null) {
                throw new RuntimeException("ID do produto é obrigatório");
            }
            if (itemRequest.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade deve ser positiva");
            }

            // Cria o item usando o mapper (que já pega o valor do produto)
            ItemVenda item = itemVendaMapper.toEntity(itemRequest);
            item.setVenda(venda); // Vincula à venda

            // Verifica estoque usando o produto que já foi carregado no mapper
            if (item.getProduto().getEstoque() < itemRequest.getQuantidade()) {
                throw new RuntimeException("Estoque insuficiente");
            }

            // Atualiza estoque
            item.getProduto().setEstoque(item.getProduto().getEstoque() - itemRequest.getQuantidade());
            produtoRepository.save(item.getProduto());

            itensVenda.add(item);
            subtotal = subtotal.add(item.getValorUnitario().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }

        // Calcula valor total
        BigDecimal desconto = subtotal.multiply(venda.getDescontoPercentual().divide(BigDecimal.valueOf(100)));
        venda.setValorTotal(subtotal.subtract(desconto));
        venda.setVendas(itensVenda);

        // Persiste
        Venda vendaSalva = vendaRepository.save(venda);
        itemVendaRepository.saveAll(itensVenda);

        return vendaMapper.toResponseDTO(vendaSalva);
    }

    public VendaResponseDTO buscarVendaPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada com ID: " + id));
        return vendaMapper.toResponseDTO(venda);
    }

    public List<VendaResponseDTO> listarTodasVendas() {
        return vendaRepository.findAll().stream()
                .map(vendaMapper::toResponseDTO)
                .toList();
    }


    // Busca vendas de um cliente específico
    public List<Venda> buscarVendasPorCliente(Long clienteId) {
        return vendaRepository.findByClienteId(clienteId);
    }
}