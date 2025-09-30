package com.senai.conta_bancaria_turma1.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CORRENTE")
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
public class ContaCorrente extends Conta{

    @Column(precision = 4)
    private BigDecimal limite;

    @Column(precision = 5)
    private BigDecimal taxa;

    @Override
    public String getTipo() {
        return "CORRENTE";
    }
    @Override
    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Valor inválido");
        }
        BigDecimal custoSaque = valor.multiply(taxa);
        BigDecimal totalSaque = valor.add(custoSaque);

        if(getSaldo().add(limite).compareTo(totalSaque) < 0){
            throw new RuntimeException("Saldo insuficiente");
        }
        setSaldo(getSaldo().subtract(totalSaque));
    }

}
