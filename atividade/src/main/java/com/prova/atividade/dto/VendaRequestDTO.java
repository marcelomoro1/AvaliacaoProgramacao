package com.prova.atividade.dto;
import java.math.BigDecimal;
import java.util.List;

public class VendaRequestDTO {

    private Long clienteId;
    private List<ItemVendaRequestDTO> itens;
    private BigDecimal descontoPercentual;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemVendaRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemVendaRequestDTO> itens) {
        this.itens = itens;
    }

    public BigDecimal getDescontoPercentual() {
        return descontoPercentual;
    }

    public void setDescontoPercentual(BigDecimal descontoPercentual) {
        this.descontoPercentual = descontoPercentual;
    }


}
