package com.prova.atividade.mapper;

import com.prova.atividade.dto.ItemVendaResponseDTO;
import com.prova.atividade.dto.VendaRequestDTO;
import com.prova.atividade.dto.VendaResponseDTO;
import com.prova.atividade.model.Cliente;
import com.prova.atividade.model.ItemVenda;
import com.prova.atividade.model.Venda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VendaMapper {
    @Autowired
    private ClienteMapper clienteMapper;
    @Autowired
    private ItemVendaMapper itemVendaMapper;

    public Venda toEntity(VendaRequestDTO request) {
        Venda venda = new Venda();
        venda.setDescontoPercentual(request.getDescontoPercentual());

        // Corrigido: usar setVendas() em vez de setItens()
        if (request.getItens() != null) {
            List<ItemVenda> itens = request.getItens().stream()
                    .map(itemVendaMapper::toEntity)
                    .peek(item -> item.setVenda(venda))
                    .collect(Collectors.toList());
            venda.setVendas(itens);  // ← Aqui está a correção
        }

        return venda;
    }

    public VendaResponseDTO toResponseDTO(Venda venda) {
        VendaResponseDTO response = new VendaResponseDTO();
        response.setId(venda.getId());
        response.setData(venda.getData());
        response.setDescontoPercentual(venda.getDescontoPercentual());
        response.setValorTotal(venda.getValorTotal());

        // Cliente
        response.setCliente(clienteMapper.toResponseDTO(venda.getCliente()));

        // Itens (corrigido para usar getVendas())
        if (venda.getVendas() != null) {
            List<ItemVendaResponseDTO> itensDTO = venda.getVendas().stream()
                    .map(itemVendaMapper::toResponseDTO)
                    .collect(Collectors.toList());
            response.setItens(itensDTO);
        }

        return response;
    }
}
