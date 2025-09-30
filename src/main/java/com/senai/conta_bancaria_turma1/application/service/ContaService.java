package com.senai.conta_bancaria_turma1.application.service;

import com.senai.conta_bancaria_turma1.application.dto.ContaAtualizacaoDTO;
import com.senai.conta_bancaria_turma1.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria_turma1.application.dto.TransferenciaDTO;
import com.senai.conta_bancaria_turma1.application.dto.ValorSaqueDepositoDTO;
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
    public ContaResumoDTO atualizarConta(@PathVariable String numero, @RequestBody ContaAtualizacaoDTO dto) {
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
    public void deletarConta(@PathVariable String numero) {
        Conta conta = repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.setAtiva(false);
        repository.save(conta);
    }
    public ContaResumoDTO sacar(@PathVariable String numero, @RequestBody ValorSaqueDepositoDTO dto) {
        Conta conta = repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.sacar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }

    public ContaResumoDTO depositar(String numeroDaConta, ValorSaqueDepositoDTO dto) {
        Conta conta = repository.findByNumeroAndAtivaTrue(numeroDaConta)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
        conta.depositar(dto.valor());
        return ContaResumoDTO.fromEntity(repository.save(conta));
    }
    private Conta buscarContaAtivaPorNumero(String numero) {
        return repository.findByNumeroAndAtivaTrue(numero)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public ContaResumoDTO transferir(String numeroDaConta, TransferenciaDTO dto) {
        Conta conta = buscarContaAtivaPorNumero(numeroDaConta);
        Conta contaDestino = buscarContaAtivaPorNumero(dto.numeroContaDestino());
        conta.sacar(dto.valor());
        contaDestino.depositar(dto.valor());
        repository.save(conta);
        repository.save(contaDestino);
        return ContaResumoDTO.fromEntity(conta);

    }
}
