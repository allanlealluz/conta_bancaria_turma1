package com.senai.conta_bancaria_turma1.application.service;

import com.senai.conta_bancaria_turma1.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria_turma1.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria_turma1.domain.entity.Cliente;
import com.senai.conta_bancaria_turma1.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    // Registrar um novo cliente ou adicionar nova conta
    public ResponseEntity<ClienteResponseDTO> registrarCliente(ClienteRegistroDTO dto) {

        Cliente cliente = repository.findByCpfAndAtivoTrue(dto.cpf())
                .orElseGet(() -> repository.save(dto.toEntity()));

        var contas = cliente.getContas();
        var novaConta = dto.contaDTO().toEntity(cliente);

        boolean jaTemTipo = contas.stream()
                .anyMatch(c -> c.getClass().equals(novaConta.getClass()) && c.isAtiva());

        if (jaTemTipo)
            throw new RuntimeException("Cliente já possui uma conta desse tipo.");

        cliente.getContas().add(novaConta);
        Cliente clienteSalvo = repository.save(cliente);

        return ClienteResponseDTO.fromEntity(clienteSalvo);
    }

    // Obter cliente pelo ID
    public ResponseEntity<ClienteResponseDTO> obterCliente(String id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return ClienteResponseDTO.fromEntity(cliente);
    }

    // Remover cliente (marcando como inativo)
    public ResponseEntity<Void> removerCliente(String id) {
        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        cliente.setAtivo(false); // supondo que o Cliente tenha o campo "ativo"
        repository.save(cliente);
        return ResponseEntity.ok().build();
    }

    // Atualizar dados do cliente
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(String cpf, ClienteRegistroDTO dto) {
        Cliente cliente = repository.findByCpfAndAtivoTrue(cpf)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        cliente.setNome(dto.nome());
        cliente.setCpf(dto.cpf());

        Cliente clienteSalvo = repository.save(cliente);
        return ClienteResponseDTO.fromEntity(clienteSalvo);
    }
}
