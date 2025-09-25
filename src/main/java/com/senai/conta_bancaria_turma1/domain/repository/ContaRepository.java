package com.senai.conta_bancaria_turma1.domain.repository;

import com.senai.conta_bancaria_turma1.domain.entity.Cliente;
import com.senai.conta_bancaria_turma1.domain.entity.Conta;
import java.util.Arrays;
import java.util.List;

public interface ContaRepository {

    List<Cliente> findAllByAtivaTrue();

    Cliente findByNumeroAndAtivaTrue(String numero);
}
