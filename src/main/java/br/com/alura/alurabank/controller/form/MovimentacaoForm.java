package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;
import br.com.alura.alurabank.dominio.Operacao;

import java.math.BigDecimal;

public class MovimentacaoForm {
    private DadosDaConta dadosDaConta;
    private BigDecimal valor;

    public Operacao getOperacao() {
        return operacao;
    }

    public void setOperacao(Operacao operacao) {
        this.operacao = operacao;
    }

    private Operacao operacao;

    public DadosDaConta getDadosDaConta() {
        return dadosDaConta;
    }

    public void setDadosDaConta(DadosDaConta dadosDaConta) {
        this.dadosDaConta = dadosDaConta;
    }


    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
