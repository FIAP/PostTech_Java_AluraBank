package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;

import java.math.BigDecimal;

public class TransferenciaForm {
    private DadosDaConta dadosDeOrigem;

    private DadosDaConta dadosDeDestino;

    private BigDecimal valor;

    public DadosDaConta getDadosDeOrigem() {
        return dadosDeOrigem;
    }

    public void setDadosDeOrigem(DadosDaConta dadosDeOrigem) {
        this.dadosDeOrigem = dadosDeOrigem;
    }

    public DadosDaConta getDadosDeDestino() {
        return dadosDeDestino;
    }

    public void setDadosDeDestino(DadosDaConta dadosDeDestino) {
        this.dadosDeDestino = dadosDeDestino;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
