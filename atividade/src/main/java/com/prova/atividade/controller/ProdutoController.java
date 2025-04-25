package com.prova.atividade.controller;

import com.prova.atividade.dto.ProdutoRequestDTO;
import com.prova.atividade.dto.ProdutoResponseDTO;
import com.prova.atividade.model.Produto;
import com.prova.atividade.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    //Criar
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody ProdutoRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.criar(request));
    }

    //Buscar pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    //Get All
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO request
    ) {
        return ResponseEntity.ok(produtoService.atualizar(id, request));
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /produtos/estoque-abaixo?quantidade=50
    @GetMapping("/estoque-abaixo")
    public List<Produto> getProdutosComEstoqueAbaixoDe(
            @RequestParam int quantidade
    ) {
        return produtoService.buscarComEstoqueAbaixoDe(quantidade);
    }

    // GET /produtos/por-nome?nome=celular
    @GetMapping("/por-nome")
    public List<Produto> getProdutosPorNome(
            @RequestParam String nome
    ) {
        return produtoService.buscarPorNome(nome);
    }

}
