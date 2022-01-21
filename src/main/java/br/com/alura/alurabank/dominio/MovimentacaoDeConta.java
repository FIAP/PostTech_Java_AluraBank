package br.com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.Assert;

import java.math.BigDecimal;

public class MovimentacaoDeConta {

    @JsonProperty
    private ContaCorrente conta;

    @JsonProperty
    private BigDecimal valor;

    @JsonProperty
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

    public String getBanco() {
        return conta.getBanco();
    }

    public String getAgencia() {
        return conta.getAgencia();
    }

    public String getNumero() {
        return conta.getNumero();
    }

    public void executarEm(ContaCorrente contaCorrente) {
         contaCorrente.executar(operacao, valor);
    }
}
