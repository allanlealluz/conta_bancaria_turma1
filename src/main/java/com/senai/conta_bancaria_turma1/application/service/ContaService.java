package com.senai.conta_bancaria_turma1.application.service;

import com.senai.conta_bancaria_turma1.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria_turma1.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria_turma1.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository repository;
    private final ClienteService clienteService;
    public List<ContaResumoDTO> criarConta(ClienteResponseDTO dto) {
        var cliente = clienteService.buscarClienteAtivoPorCpf(dto.cpf());
        var contas = cliente.contas();

        return contas;

    }

}
