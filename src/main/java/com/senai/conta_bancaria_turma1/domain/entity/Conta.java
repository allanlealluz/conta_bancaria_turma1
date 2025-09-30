package com.senai.conta_bancaria_turma1.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_conta", discriminatorType = DiscriminatorType.STRING, length = 20)
@Table(name = "conta",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_conta_numero", columnNames = "numero"),
                @UniqueConstraint(name = "uk_cliente_tipo", columnNames = {"cliente_id", "tipo_conta"})
        }
)
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, precision = 4)
    private BigDecimal saldo;

    @Column(nullable = false)
    private boolean ativa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "fk_conta_cliente"))
    private Cliente cliente;

    public abstract String getTipo() ;
    public void sacar(BigDecimal valor) {
        ValidarValorMaiorQueZero(valor);
        if (saldo.compareTo(valor) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        ValidarValorMaiorQueZero(valor);
        saldo = saldo.add(valor);
    }

    private static void ValidarValorMaiorQueZero(BigDecimal valor) {
        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor deve ser maior que zero");
        }
    }

}
