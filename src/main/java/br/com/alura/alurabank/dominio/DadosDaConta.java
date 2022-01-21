package br.com.alura.alurabank.dominio;

import java.util.Objects;

public class DadosDaConta {
    private String banco;
    private String agencia;
    private String numero;

    public DadosDaConta(String banco, String agencia, String numero) {
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

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DadosDaConta that = (DadosDaConta) o;
        return banco.equals(that.banco) && agencia.equals(that.agencia) && numero.equals(that.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(banco, agencia, numero);
    }
}
