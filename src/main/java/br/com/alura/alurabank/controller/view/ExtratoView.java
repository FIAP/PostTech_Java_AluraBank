package br.com.alura.alurabank.controller.view;

import br.com.alura.alurabank.dominio.DadosDaConta;

import java.math.BigDecimal;
import java.util.List;

public class ExtratoView {
    private DadosDaConta dadosDaConta;
    private List<BigDecimal> valores;
    private BigDecimal saldo;

    public ExtratoView(DadosDaConta dadosDaConta, List<BigDecimal> valores, BigDecimal saldo) {
        this.dadosDaConta = dadosDaConta;
        this.valores = valores;
        this.saldo = saldo;
    }

    public String getBanco() {
        return dadosDaConta.getBanco();
    }

    public String getAgencia() {
        return dadosDaConta.getAgencia();
    }

    public String getNumero() {
        return dadosDaConta.getNumero();
    }

    public List<BigDecimal> getValores() {
        return valores;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
