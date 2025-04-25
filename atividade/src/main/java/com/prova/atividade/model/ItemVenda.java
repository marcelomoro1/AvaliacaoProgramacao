package com.prova.atividade.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class ItemVenda {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantidade;
    private BigDecimal valorUnitario;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

}
