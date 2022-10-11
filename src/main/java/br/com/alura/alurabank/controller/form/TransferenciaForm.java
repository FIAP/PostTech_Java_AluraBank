package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferenciaForm {
    @NotNull
    private DadosDaConta dadosDeOrigem;

    @NotNull
    private DadosDaConta dadosDeDestino;


    @DecimalMin("0.1")
    @NotNull
    private BigDecimal valor;

    TransferenciaForm() {
    }

    public TransferenciaForm(DadosDaConta dadosDeOrigem, DadosDaConta dadosDeDestino, BigDecimal valor) {
        this.dadosDeOrigem = dadosDeOrigem;
        this.dadosDeDestino = dadosDeDestino;
        this.valor = valor;
    }

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
