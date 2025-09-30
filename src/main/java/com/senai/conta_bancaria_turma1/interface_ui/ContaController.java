package com.senai.conta_bancaria_turma1.interface_ui;

import com.senai.conta_bancaria_turma1.application.dto.ContaResumoDTO;
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
    public ResponseEntity<ContaService> atualizarConta(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service);
    }
    @DeleteMapping("/{numeroDaConta}")
    public ResponseEntity<ContaService> deletarConta(@PathVariable String numeroDaConta) {
        return ResponseEntity.ok(service);
    }
}
