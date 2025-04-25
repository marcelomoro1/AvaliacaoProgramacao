package com.prova.atividade.controller;

import com.prova.atividade.dto.ItemVendaRequestDTO;
import com.prova.atividade.dto.ItemVendaResponseDTO;
import com.prova.atividade.service.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-venda")
@RequiredArgsConstructor
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    @PostMapping
    public ResponseEntity<ItemVendaResponseDTO> criarItem(
            @RequestBody ItemVendaRequestDTO request,
            @RequestParam Long vendaId
    ) {
        ItemVendaResponseDTO response = itemVendaService.criar(request, vendaId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemVendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(itemVendaService.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItem(@PathVariable Long id) {
        itemVendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/por-venda/{vendaId}")
    public ResponseEntity<List<ItemVendaResponseDTO>> listarPorVenda(
            @PathVariable Long vendaId
    ) {
        return ResponseEntity.ok(itemVendaService.listarPorVenda(vendaId));
    }
}