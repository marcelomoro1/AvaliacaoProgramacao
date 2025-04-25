package com.prova.atividade.service;

import com.prova.atividade.dto.ClienteRequestDTO;
import com.prova.atividade.dto.ClienteResponseDTO;
import com.prova.atividade.mapper.ClienteMapper;
import com.prova.atividade.model.Cliente;
import com.prova.atividade.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    // Criar cliente
    public ClienteResponseDTO criar(ClienteRequestDTO request) {
        Cliente cliente = clienteMapper.toEntity(request);
        return clienteMapper.toResponseDTO(clienteRepository.save(cliente));
    }

    // Buscar por ID
    public ClienteResponseDTO buscarPorId(Long id) {
        return clienteMapper.toResponseDTO(
                clienteRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"))
        );
    }

    // Listar todos
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    // Deletar
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    // Atualizar
    public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());

        return clienteMapper.toResponseDTO(clienteRepository.save(cliente));
    }
}
