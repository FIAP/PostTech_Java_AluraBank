package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class MovimentacaoDeConta {

    @Id
    @GeneratedValue
    private Integer id;


    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Operacao operacao;

    protected MovimentacaoDeConta() {
    }

    public MovimentacaoDeConta(BigDecimal valor, Operacao operacao) {
        Assert.notNull(valor, "Valor não pode ser nulo");
        Assert.isTrue(valor.compareTo(BigDecimal.ZERO) > 0, "Valor deve ser maior que zero");
        Assert.notNull(operacao, "Operação não pode ser nula");

        this.valor = valor;
        this.operacao = operacao;
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

}
