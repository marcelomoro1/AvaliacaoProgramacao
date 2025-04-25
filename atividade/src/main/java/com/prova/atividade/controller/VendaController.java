package com.prova.atividade.controller;

import com.prova.atividade.dto.VendaRequestDTO;
import com.prova.atividade.dto.VendaResponseDTO;
import com.prova.atividade.model.Venda;
import com.prova.atividade.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {
    private final VendaService vendaService;

    //Criar
    @PostMapping
    public ResponseEntity<VendaResponseDTO> criarVenda(@RequestBody VendaRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.criarVenda(request));
    }

    //Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarVendaPorId(id));
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodasVendas());
    }

    // Exemplo: GET /vendas/cliente/1
    @GetMapping("/cliente/{clienteId}")
    public List<Venda> getVendasPorCliente(
            @PathVariable Long clienteId
    ) {
        return vendaService.buscarVendasPorCliente(clienteId);
    }
}