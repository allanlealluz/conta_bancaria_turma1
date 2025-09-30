package com.senai.conta_bancaria_turma1.interface_ui;

import com.senai.conta_bancaria_turma1.application.dto.ContaAtualizacaoDTO;
import com.senai.conta_bancaria_turma1.application.dto.ContaResumoDTO;
import com.senai.conta_bancaria_turma1.application.dto.ValorSaqueDepositoDTO;
import com.senai.conta_bancaria_turma1.application.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    @GetMapping
    public ResponseEntity<List<ContaResumoDTO>> listarContas() {
        return ResponseEntity.ok(service.listarTodasContas());
    }
    @PostMapping
    public ResponseEntity<ContaService> criarConta() {
        return ResponseEntity.ok(service);
    }
    @GetMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> buscarConta(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service.buscarContaPorNumero(numeroDaConta));
    }
    @PutMapping("/{numeroDaConta}")
    public ResponseEntity<ContaResumoDTO> atualizarConta(@PathVariable String numeroDaConta, @RequestBody ContaAtualizacaoDTO dto) {
        return ResponseEntity.ok(service.atualizarConta(numeroDaConta, dto));
    }
    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<ContaService> deletarConta(@PathVariable String numeroDaConta) {
        service.deletarConta(numeroDaConta);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{numeroDaConta}/saque")
    public ResponseEntity<ContaResumoDTO> sacar(@PathVariable String numeroDaConta, @RequestBody ValorSaqueDepositoDTO dto) {
        return ResponseEntity.ok(service.sacar(numeroDaConta, dto));
    }
    @PutMapping("/{numeroDaConta}/deposito")
    public ResponseEntity<ContaService> depositar(@PathVariable String numeroDaConta, @RequestBody ValorSaqueDepositoDTO dto) {
        service.depositar(numeroDaConta, dto);
        return ResponseEntity.ok(service);
    }
}
