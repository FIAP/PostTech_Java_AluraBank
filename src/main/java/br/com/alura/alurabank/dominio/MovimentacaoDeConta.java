package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class MovimentacaoDeConta {

    private ContaCorrente conta;

    private BigDecimal valor;

    private Operacao operacao;

    public MovimentacaoDeConta(ContaCorrente conta, BigDecimal valor, Operacao operacao) {
        Assert.notNull(conta, "Conta não pode ser nula");
        Assert.notNull(valor, "Valor não pode ser nulo");
        Assert.isTrue(valor.compareTo(BigDecimal.ZERO) > 0, "Valor deve ser maior que zero");
        Assert.notNull(operacao, "Operação não pode ser nula");

        this.conta = conta;
        this.valor = valor;
        this.operacao = operacao;
    }

    public int obterNumeroConta() {
        return conta.obterNumeroConta();
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
