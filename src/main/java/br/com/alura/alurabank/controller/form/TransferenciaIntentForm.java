package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferenciaIntentForm {

    @NotNull
    private DadosDaConta dadosDaConta;

    @NotNull
    @DecimalMin("0.1")
    private BigDecimal valor;


    public DadosDaConta getDadosDaConta() {
        return dadosDaConta;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
