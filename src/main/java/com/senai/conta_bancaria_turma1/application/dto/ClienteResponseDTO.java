package com.senai.conta_bancaria_turma1.application.dto;

import com.senai.conta_bancaria_turma1.domain.entity.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public record ClienteResponseDTO(
        String id,
        String nome,
        String cpf,
        List<ContaResumoDTO> contas
) {
    public static ResponseEntity<ClienteResponseDTO> fromEntity(Cliente cliente) {
        List<ContaResumoDTO> contas = cliente.getContas().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();

        ClienteResponseDTO dto = new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                contas
        );

        return ResponseEntity.ok(dto);
    }
}
