package com.senai.conta_bancaria_turma1.interface_ui;

import com.senai.conta_bancaria_turma1.application.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conta")
@RequiredArgsConstructor
public class ContaController {
    private final ContaService service;

    @GetMapping
    public ResponseEntity<ContaService> listarContas() {
        return ResponseEntity.ok(service);
    }
    @PostMapping
    public ResponseEntity<ContaService> criarConta() {
        return ResponseEntity.ok(service);
    }
}
