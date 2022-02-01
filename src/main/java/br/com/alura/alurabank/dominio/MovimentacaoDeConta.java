package br.com.alura.alurabank.dominio;

import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class MovimentacaoDeConta {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private ContaCorrente conta;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Operacao operacao;


    private LocalDateTime data = LocalDateTime.now();

    protected MovimentacaoDeConta() {
    }

    public MovimentacaoDeConta(ContaCorrente conta, BigDecimal valor, Operacao operacao) {
        Assert.notNull(valor, "Valor não pode ser nulo");
        Assert.isTrue(valor.compareTo(BigDecimal.ZERO) > 0, "Valor deve ser maior que zero");
        Assert.notNull(operacao, "Operação não pode ser nula");

        this.valor = valor;
        this.operacao = operacao;
        this.conta = conta;
    }

    public Operacao getOperacao() {
        return operacao;
    }

    public BigDecimal getValor() {
        if (operacao.equals(Operacao.SAQUE)) {
            return valor.negate();
        }

        return valor;
    }

    public LocalDateTime getData() {
        return data;
    }
}
