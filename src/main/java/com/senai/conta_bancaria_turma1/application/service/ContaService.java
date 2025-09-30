package com.senai.conta_bancaria_turma1.application.service;

import com.senai.conta_bancaria_turma1.application.dto.ClienteAtualizacaoDTO;
import com.senai.conta_bancaria_turma1.application.dto.ClienteAtualizadoDTO;
import com.senai.conta_bancaria_turma1.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria_turma1.domain.entity.Conta;
import com.senai.conta_bancaria_turma1.domain.entity.ContaCorrente;
import com.senai.conta_bancaria_turma1.domain.entity.ContaPoupanca;
import com.senai.conta_bancaria_turma1.domain.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContaService {
    private final ContaRepository repository;

    @Transactional(readOnly = true)
    public List<ContaResumoDTO> listarTodasContas() {
        return repository.findAllByAtivaTrue().stream()
                .map(ContaResumoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ContaResumoDTO buscarContaPorNumero(String numero) {
        return ContaResumoDTO.fromEntity(
                repository.findByNumeroAndAtivaTrue(numero)
                        .orElseThrow(() -> new RuntimeException("Conta não encontrada"))
        );
    }
    public ContaResumoDTO atualizarConta(@PathVariable String numero, @RequestBody ClienteAtualizacaoDTO dto) {
        Conta conta = repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
       if(conta instanceof ContaPoupanca poupanca){
           poupanca.setRendimento(dto.rendimento());
       } else if(conta instanceof ContaCorrente corrente){
           corrente.setLimite(dto.limite());
       }
       conta.setSaldo(dto.saldo());
       return ContaResumoDTO.fromEntity(repository.save(conta));
    }
}
