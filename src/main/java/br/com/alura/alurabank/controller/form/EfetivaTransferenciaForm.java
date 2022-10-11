package br.com.alura.alurabank.controller.form;

import br.com.alura.alurabank.dominio.DadosDaConta;

import javax.validation.constraints.NotNull;

public class EfetivaTransferenciaForm {
    @NotNull
    private DadosDaConta dadosDaConta;

    public DadosDaConta getDadosDaConta() {
        return dadosDaConta;
    }

}
