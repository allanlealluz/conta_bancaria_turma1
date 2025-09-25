package com.senai.conta_bancaria_turma1.interface_ui;

import com.senai.conta_bancaria_turma1.application.dto.ClienteRegistroDTO;
import com.senai.conta_bancaria_turma1.application.dto.ClienteResponseDTO;
import com.senai.conta_bancaria_turma1.application.service.ClienteService;
import com.senai.conta_bancaria_turma1.domain.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> registrarCliente(@RequestBody ClienteRegistroDTO dto) {
        return service.registrarCliente(dto);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obterCliente(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerCliente(@PathVariable String id) {
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarCliente(@PathVariable String cpf) {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Void> listarClientes() {
        return ResponseEntity.ok().build();
    }

}
