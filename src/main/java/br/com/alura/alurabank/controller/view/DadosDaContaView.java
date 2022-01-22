package br.com.alura.alurabank.controller.view;

public class DadosDaContaView {
    private String banco;
    private String agencia;
    private String numero;

    public DadosDaContaView(String banco, String agencia, String numero) {
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getNumero() {
        return numero;
    }
}
